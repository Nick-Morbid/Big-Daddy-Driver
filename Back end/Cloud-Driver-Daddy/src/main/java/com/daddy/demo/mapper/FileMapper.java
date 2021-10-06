package com.daddy.demo.mapper;

import com.daddy.demo.entity.BO.Files;
import com.daddy.demo.entity.POJO.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FileMapper {
    List<Files> selectFilesByuId(Integer uId);
    void updateFile(File file);

    void insertFile(File file);

    void insertFiles(@Param("files") List<File> filesinfo);

    File selectFileById(Integer id);

    List<Files> selectShareFilesByuId(Integer uId);

    List<Files> selectDeleteFilesByuId(Integer uId);

    List<File> selectChildrenFiles(Integer id);

}
