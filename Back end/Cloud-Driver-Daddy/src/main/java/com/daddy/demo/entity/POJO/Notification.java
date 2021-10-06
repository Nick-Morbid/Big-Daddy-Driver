package com.daddy.demo.entity.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

//这个是通知信息类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification implements Serializable {
    private Integer id;
    private String body;
    private Integer type;
    private Integer senderId;
    private Integer receiverId;
    private Date timestamp;
}
