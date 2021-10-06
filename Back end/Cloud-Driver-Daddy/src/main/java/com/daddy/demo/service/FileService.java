package com.daddy.demo.service;

import com.daddy.demo.customSettings.Error.ServiceCommon;
import com.daddy.demo.customSettings.Exception.ServiceException;
import com.daddy.demo.entity.BO.Files;
import com.daddy.demo.entity.POJO.File;
import com.daddy.demo.entity.POJO.Notification;
import com.daddy.demo.mapper.FileMapper;
import com.daddy.demo.mapper.NotificationMapper;
import com.daddy.demo.mapper.UserMapper;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileService {

    @Autowired
    FileMapper fileMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    //    根目录
    private final static String rootPath = "E:/IntelliJ IDEA/大二上学习项目/Cloud-Driver-Daddy/Files";
    public List<Files> getFiles(Integer uId) {
        return fileMapper.selectFilesByuId(uId);
    }

    public void RenameFile(File file) {
        fileMapper.updateFile(file);
    }

    @Transactional
    public void UpdateFileState(File file) {
        if (file.getState()==-1) file.setDeletetime(new Date(System.currentTimeMillis()));
        if (file.getSuffix().equals("*")){
            UpdateChildrenFileState(file);
        }else{
//            单文件的设为共享，需要考虑是否要将父文件也设为共享
            if (file.getState()==1){
                fileMapper.updateFile(file);
                UpdateFatherStateToShare(file);
//            单文件的恢复，需要考虑是否要将父文件也恢复（若父文件为删除，说明应该恢复）
            }else if (file.getState()==0){
                fileMapper.updateFile(file);
                UpdateFatherStateToShare(file);
            }else {
//                单文件的删除没有什么特殊的地方
                fileMapper.updateFile(file);
            }
        }
    }
//    修改子文件的状态
    @Transactional
    public void UpdateChildrenFileState(File file){
        if (file.getSuffix().equals("*")){
//            将文件夹及其子文件恢复、删除或设为共享
//            为什么要把文件夹和文件放在一张表啊啊啊
//            得到file对象对应的子文件
            List<File> childrenFiles = fileMapper.selectChildrenFiles(file.getId());
            for (File file1 : childrenFiles) {
                if (file.getState()==0){
//                说明是恢复删除的文件夹及其子文件
                    if (file.getDeletetime()!=null){
                        file1.setState(0);
                    }else {
//                        说明是要恢复不共享状态（处于删除状态的文件不能处理）
                        if (file1.getState()==1) file1.setState(0);
                        else continue;
                    }
                }else if (file.getState()==1){
//                    只对处于非共享状态的文件修改状态（处于删除状态的文件不能处理）
                    if (file1.getState()==0) file1.setState(1);
                    else continue;
                }else if (file.getState()==-1){
//                    说明是删除该文件夹
                    file1.setState(-1);
                    file1.setDeletetime(new Date(System.currentTimeMillis()));
                }
//                递归调用，对该子文件进行相同处理（若子文件为文件夹，则需要查询其子文件）
                UpdateChildrenFileState(file1);
            }
        }
//        针对一个文件进行恢复、删除或设为共享
        fileMapper.updateFile(file);
    }
//    修改父文件的状态（主要是针对在文件夹中将文件设为共享，需要将文件夹也同步为共享的情形。还有子文件恢复，需要将父文件也设为恢复的情形）
    @Transactional
    public void UpdateFatherStateToShare(File file){
        if (file.getUpId()!=-1){
            File fatherfile = fileMapper.selectFileById(file.getUpId());
            if (file.getState()==1&&fatherfile.getState()==0){
                fatherfile.setState(1);
            }else if (file.getState()==0&&fatherfile.getState()==-1){
                fatherfile.setState(0);
//                删除时间不用刻意置空，默认调用修改sql的话，如果没有给删除时间赋值，则会自动置空
//                fatherfile.setDeletetime(null);
            }
            fileMapper.updateFile(fatherfile);
            UpdateFatherStateToShare(fatherfile);
        }
    }

    public List<Files> BuildFolder(File file) {
        file.setState(0);//默认是非共享状态
        file.setSuffix("*");//文件的后缀为*
        file.setBuildtime(new Date(System.currentTimeMillis()));
        fileMapper.insertFile(file);
        return getFiles(file.getUId());
    }

    public void UploadFiles(List<MultipartFile> files, List<File> filesinfo, Integer id) {
        if (files!=null&&files.size()>0){
            for (MultipartFile file : files) {
//                求出存储路径（已经存到了mysql中）
                String path = getPath(filesinfo, file.getOriginalFilename());
                if (path.equals("/")) path="";
//    检测用户分配目录是否创建（在根目录下用"UUID"+id来区分每个用户的文件）
                String userPath = rootPath + "/UUID"+id + path;
                java.io.File fileDir = new java.io.File(userPath);
                if (!fileDir.exists() && !fileDir.isDirectory()) {
                    fileDir.mkdirs();
                }
                String storePath = userPath + "/"+file.getOriginalFilename();
                System.out.println("文件"+file.getOriginalFilename()+" 已保存至 "+storePath);
//                这步的作用是把文件写入我们设置的目录下
                try {
                    file.transferTo(new java.io.File(storePath));
                } catch (IOException e) {
                    throw new ServiceException(ServiceCommon.FILE_UPLOAD_FAIL);
                }
            }
        }
        for (File file : filesinfo) {
            file.setState(0);//默认是非共享状态
            String suffix = getSuffix(file.getName());
            file.setSuffix(suffix);
            file.setUId(id);
            file.setBuildtime(new Date(System.currentTimeMillis()));
            if (suffix.equals("jpg")||suffix.equals("png")||suffix.equals("jpeg")){
                file.setType("picture");
            }else {
                file.setType("file");
            }
            if (!file.getPath().equals("/"))
                file.setPath(file.getPath()+"/"+file.getName());
            else file.setPath("/"+file.getName());
            System.out.println(file.getPath());
        }
        System.out.println(filesinfo);
        fileMapper.insertFiles(filesinfo);

    }

    private String getSuffix(String name){
//        获取后缀的方法
        int id = name.lastIndexOf(".");
        String suffix = name.substring(id+1).toUpperCase();
        return suffix.toLowerCase();
    }
    //    从文件信息中获取文件的相对路径
    private String getPath(List<File> filesinfo,String name){
        for (File file : filesinfo) {
            if (file.getName().equals(name)){
                return file.getPath();
            }
        }
        return "";
    }

    public void DownloadFile(HttpServletRequest request,HttpServletResponse response, Integer fileId, Integer uId,Integer id) {//第一个是用户id，第二个是文件id
        String downloadURL = rootPath + "/UUID"+ uId;
        File file = fileMapper.selectFileById(fileId);
        downloadURL+=file.getPath();
        System.out.println(downloadURL);

        ServletOutputStream os = null;
        InputStream is= null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
//            response.reset();//这句要注释掉，不然会导致跨域请求头失效
            response.setContentType("content-type:application/octet-stream");
            //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，
            // 保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            //把文件名按UTF-8取出，并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(file.getName().getBytes("utf-8"),"ISO8859-1"));
            //读取流
            java.io.File f = new java.io.File(downloadURL);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败，请检查文件“" + file.getName() + "”是否存在");
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
//            throw new ServiceException(ServiceCommon.FILE_DOWNLOAD_FAIL);
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (uId!=id){
            Notification fileDownNotice = new Notification(null,userMapper.selectById(id).getName()+"下载了您的文件："+file.getName(),2,id,uId,new Date(System.currentTimeMillis()));
            notificationMapper.insertNotification(fileDownNotice);
        }
    }

    public List<Files> GetShareFiles(Integer id) {
//        只会获取朋友共享的文件（如果文件夹没有共享，只共享文件，则会查询不到）
       return fileMapper.selectShareFilesByuId(id);
    }

    public List<Files> GetDeleteFilesByuId(Integer id){
        return fileMapper.selectDeleteFilesByuId(id);
    }

}
