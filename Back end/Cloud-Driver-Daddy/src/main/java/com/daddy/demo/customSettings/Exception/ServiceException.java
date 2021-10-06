package com.daddy.demo.customSettings.Exception;


import com.daddy.demo.customSettings.Error.BaseErrorInfoInterface;

//业务异常
public class ServiceException extends RuntimeException implements BaseExceptionInterface{

    private String code;
    private String msg;

    public ServiceException(){
    }
    public ServiceException(String msg){
        super(msg);
        this.msg = msg;
    }

    public ServiceException(BaseErrorInfoInterface baseErrorInfoInterface){
        this.code = baseErrorInfoInterface.getCode();
        this.msg = baseErrorInfoInterface.getMsg();
    }

    public ServiceException(String code,String msg){
        super(msg);
        this.code = code;
        this.msg = msg;
    }
    public ServiceException(String code,String msg,Throwable cause){
        super(code,cause);
        this.code = code;
        this.msg = msg;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



}
