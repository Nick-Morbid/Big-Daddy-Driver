package com.daddy.demo.handler;

import com.alibaba.fastjson.JSON;
import com.daddy.demo.customSettings.Error.ServiceCommon;
import com.daddy.demo.entity.POJO.User;
import com.daddy.demo.entity.VO.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.LinkedHashMap;

//全局的返回结果封装类
@RestControllerAdvice
//由于我们这个注解没有加上指定生效的包名（对全局拓展），故会和某些功能产生冲突，如使用swagger出现空白页异常。
public class GlobalResponseHandler implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        /*
        是否开启advice支持
        true：支持，false：不支持
         */

        return true;
    }

    /*
    处理response的具体业务方法
     */

//    可以在这里统一包装数据，并做数据脱敏
    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

//        检测是否有进入过全局异常处理类
        if (o instanceof Result){
            return o;
        }

        if (o instanceof String){
            return JSON.toJSONString(o);
        }

        if (o instanceof User){
            if (((User) o).getPassword()!=null&&!((User) o).getPassword().equals("")){
                ((User) o).setPassword(null);
            }
        }

//       检测是否在进入控制器前发生错误（这里可以将服务器的其他的错误都检测出来）
        if (o!=null && o.getClass()== LinkedHashMap.class && ((LinkedHashMap<?, ?>) o).get("status") !=null){
            String status = String.valueOf(((LinkedHashMap<?, ?>) o).get("status"));
            String error = String.valueOf(((LinkedHashMap<?, ?>) o).get("error"));
            return new Result(status,error);
        }
        return new Result(ServiceCommon.SUCCESS,o);
    }
}
