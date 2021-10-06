package com.daddy.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {

    //    设置过期时间（三十分钟过期）
    private static final long EXPIER_TIME = 300*60*1000;//先改为五小时过期
    //    设置私钥
    private static final String TOKEN_SECRET = "2ERQFA32QR3T~!>S>{SDQW@";
    public static String getToken(int id,String name) {

//        设置token的header（下面的其实为默认格式）
        Map<String ,Object> header = new HashMap<>(2);
        header.put("typ","JWT");
        header.put("alg","HS256");

//        设置过期时间和加密算法
        Date date = new Date(System.currentTimeMillis()+EXPIER_TIME);
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        String token = JWT.create()
//                插入头信息
                .withHeader(header)
//                插入信息
                .withClaim("id",id)
                .withClaim("name",name)
//                声明有效时间
                .withExpiresAt(date)
//                声明尾部的签名（包含算法和私钥）
                .sign(algorithm);
        System.out.println(token);
        return token;
    }
//    验证签名
    public  static DecodedJWT verify(String token){
//        创建验证对象
//        如果token没错，会返回一个DecodedJWT对象，可以用来查询token中的数据；否则会报错
        return JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build().verify(token);
    }
}
