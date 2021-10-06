package com.daddy.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.daddy.demo.customSettings.Error.ServiceCommon;
import com.daddy.demo.customSettings.Exception.ServiceException;
import com.daddy.demo.entity.BO.Files;
import com.daddy.demo.entity.DTO.FileInfo;
import com.daddy.demo.entity.POJO.File;
import com.daddy.demo.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.LabelUI;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/getFiles")
    public List<Files> getFiles(HttpServletRequest request){
        return fileService.getFiles((Integer) request.getSession().getAttribute("id"));
    }
    @PutMapping("/renameFile")
    public void RenameFile(@RequestBody File file){
        fileService.RenameFile(file);
    }

    @PutMapping("/updateFileState")
    public void UpdateFileState(@RequestBody File file){
        fileService.UpdateFileState(file);
    }
    @PostMapping("/buildFolder")
    public List<Files>  BuildFolder(@RequestBody File file,HttpServletRequest request){
        file.setUId((Integer) request.getSession().getAttribute("id"));
        return fileService.BuildFolder(file);
    }
    @PostMapping("/uploadFiles")
    public void UploadFiles(HttpServletRequest request){
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("files");
//        for (MultipartFile file : files) {
//            System.out.println(file.getOriginalFilename());
//        }
//        由JSON字符串转成java对象，需要传入类的字节码！！！
        List<File> filesinfo = JSON.parseArray(params.getParameter("filesinfo"),File.class);
//        for (File file : fileList) {
//            System.out.println(file);
//        }
        fileService.UploadFiles(files,filesinfo,(Integer)request.getSession().getAttribute("id"));
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(@RequestParam("fileId") Integer fileId,@RequestParam("uId") Integer uId, HttpServletRequest request,HttpServletResponse response){
//        用-1来标记是否是在下载本人的文件
        if (uId==-1) uId = (Integer) request.getSession().getAttribute("id");
        fileService.DownloadFile(request,response,fileId, uId,(Integer) request.getSession().getAttribute("id"));
    }

    @GetMapping("/getShareFiles")
    public List<Files> GetFriendFiles(@RequestParam("id")Integer id,HttpServletRequest request){
        if (id==-1) id = (Integer) request.getSession().getAttribute("id");
       return fileService.GetShareFiles(id);
    }

    @GetMapping("/getDeleteFiles")
    public List<Files> GetDeleteFiles(HttpServletRequest request){
        return fileService.GetDeleteFilesByuId((Integer) request.getSession().getAttribute("id"));
    }


}
