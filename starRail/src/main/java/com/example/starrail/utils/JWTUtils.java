package com.example.starrail.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JWTUtils {
    private static String signKey = "dalan";
    private static Long expire = 4320000L;


    /*生成JWT令牌*/
    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)//设置签名算法和秘钥
                .setExpiration(new Date(System.currentTimeMillis() + expire))//设置有效期
                .compact();
        return jwt;
    }

    /*解析JWT令牌*/
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)//注意这个方法后面这里是Jws！！s！！不是t！！
                .getBody();
        return claims;
    }



    /*public static void generateJwtTest(){
        Map<String, Object> claims = new HashMap<>();
        claims.put("id","123");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, "dalan")//设置签名算法和秘钥
                .setClaims(claims)//设置自定义内容
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))//设置有效期
                .compact();
        System.out.println(jwt);
    }*/
}
