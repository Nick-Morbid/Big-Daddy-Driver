package com.daddy.demo.entity.POJO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//朋友关系表
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    private Integer id;
    private Integer uId;//用户id
    private Integer friendId;//朋友的id
}
