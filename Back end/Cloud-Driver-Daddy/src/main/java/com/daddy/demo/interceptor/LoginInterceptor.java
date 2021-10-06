package com.daddy.demo.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.daddy.demo.customSettings.Error.TokenCommon;
import com.daddy.demo.customSettings.Exception.ServiceException;
import com.daddy.demo.utils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录认证拦截器
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        System.out.println(request.getRequestURI());

        if (request.getHeader("Authorization")==null){
            throw new ServiceException(TokenCommon.NOT_TOKEN);
        }
        //        jwt检测
        try {
            HttpSession session = request.getSession();
            session.setAttribute("id", JwtUtils.verify(request.getHeader("Authorization")).getClaim("id").asInt());
            session.setAttribute("name",JwtUtils.verify(request.getHeader("Authorization")).getClaim("name").asString());
            return true;
        }catch (SignatureVerificationException e){//无效签名
            throw new ServiceException(TokenCommon.SIGNATURE_NOT_MATCH);
        }catch (TokenExpiredException e){//token过期
            throw new ServiceException(TokenCommon.TOKEN_OVERDUE);
        }catch (AlgorithmMismatchException e){//token算法出错
            throw new ServiceException(TokenCommon.ALGORITHM_ERROR);
        }catch (Exception e){//token无效
            throw new ServiceException(TokenCommon.TOKEN_VOID);
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}
