package com.daddy.demo;

import com.daddy.demo.entity.BO.Files;
import com.daddy.demo.entity.POJO.File;
import com.daddy.demo.mapper.FileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    FileMapper fileMapper;
    @Test
    void contextLoads() {
//        List<Files> files = fileMapper.selectChildrenFiles(new File(2, null, null, null, null, null, null, null, null, null, null));
//        System.out.println(files);
    }

}
