package com.daddy.demo.handler;

import com.daddy.demo.customSettings.Error.ServiceCommon;
import com.daddy.demo.customSettings.Exception.ServiceException;
import com.daddy.demo.entity.VO.Result;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//全局异常处理类
@RestControllerAdvice
//当controller中有方法抛出异常就会来到这里
public class GlobalExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public Result ServiceExceptionHandler(ServiceException e){
        System.out.println("业务异常！原因是："+e.getMsg());
        return new Result(e);
    }

    /*
    处理空指针异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public Result NullPointerExceptionHandler(Exception e){
        System.out.println("空指针异常！原因是："+e);
        return new Result(ServiceCommon.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    public Result HttpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException e){
        System.out.println("请求格式异常！原因是："+e);
        return new Result(ServiceCommon.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = ClassCastException.class)
    public Result ClassCastExceptionHandler(ClassCastException e){
        System.out.println("请求路径异常！原因是："+e);
        return new Result("400","请求路径异常！");
    }

    /*
    处理其他异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result ExceptionHandler(Exception e){
        System.out.println("未知异常！原因是："+e);
        return new Result(ServiceCommon.INTERNAL_SERVER_ERROR);
    }

}
