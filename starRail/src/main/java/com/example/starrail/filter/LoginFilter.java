package com.example.starrail.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.starrail.common.Result;
import com.example.starrail.utils.JWTUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;//方便使用HttpServletRequest的方法
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
        res.setHeader("Access-Control-Allow-Credentials", "true");
        res.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "0");


        String url = req.getRequestURL().toString();

        if( url.contains("login") ){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }

        String jwt = req.getHeader("token");
        //判断jwt是否为空
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空");
            Result e = Result.error("需要登录");
            String s = JSONObject.toJSONString(e);
            res.getWriter().write(s);
            return;
        }

        try {
            JWTUtils.parseJWT(jwt);//解析JWT
        }catch (Exception exception){
            exception.printStackTrace();
            Result e = Result.error("令牌解析失败");
            String s = JSONObject.toJSONString(e);
            res.getWriter().write(s);
            return;
        }

        //放行
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
