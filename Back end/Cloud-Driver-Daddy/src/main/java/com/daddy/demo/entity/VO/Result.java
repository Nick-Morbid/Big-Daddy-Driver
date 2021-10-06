package com.daddy.demo.entity.VO;

import com.daddy.demo.customSettings.Error.BaseErrorInfoInterface;
import com.daddy.demo.customSettings.Exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//这个类专门用来返回数据
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    /*
    响应代码
     */

    private String code;
    /*
    响应消息
     */
    private String msg;
    /*
    响应结果
     */
    private Object result;

    public Result(BaseErrorInfoInterface baseErrorInfoInterface){
        this.code = baseErrorInfoInterface.getCode();
        this.msg = baseErrorInfoInterface.getMsg();
    }

    public Result(BaseErrorInfoInterface baseErrorInfoInterface, Object result){
        this.code = baseErrorInfoInterface.getCode();
        this.msg = baseErrorInfoInterface.getMsg();
        this.result = result;
    }

    public Result(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public Result(BaseExceptionInterface baseExceptionInterface){
        this.code = baseExceptionInterface.getCode();
        this.msg = baseExceptionInterface.getMsg();
    }

    public Result(BaseExceptionInterface baseExceptionInterface, Object result){
        this.code = baseExceptionInterface.getCode();
        this.msg = baseExceptionInterface.getMsg();
        this.result = result;
    }




}
