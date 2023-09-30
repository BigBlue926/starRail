package com.example.starrail.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.starrail.common.Result;
import com.example.starrail.pojo.DTO.UserDTO;
import com.example.starrail.utils.UserHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.starrail.utils.RedisConstants.LOGIN_USER_KEY;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    //这类不是spring创建的，不能使用注解导入StringRedisTemplate；
    private StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())){
            log.info("预请求放行");
            return true;
        }
        String url = request.getRequestURL().toString();

        if( url.contains("login") || url.contains("user") || url.contains("common")){
            log.info("路径为"+ url + "放行");
            return true;
        }

        String jwt = request.getHeader("token");
        log.info("jwt:" + jwt);
        //判断jwt是否为空
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空");
            Result e = Result.error("需要登录");
            String s = JSONObject.toJSONString(e);
            response.getWriter().write(s);
            return false;
        }
        String tokenKey = LOGIN_USER_KEY + jwt;
        //基于token获取redis之中的用户
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(tokenKey);
        log.info(userMap.toString());
        if(userMap.isEmpty()){
            log.info("拦截到了请求,请求头为空");
            Result e = Result.error("用户不存在");
            String s = JSONObject.toJSONString(e);
            response.getWriter().write(s);
            return false;
        }
        log.info("拦截到了路径："+ url + "放行");

        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        log.info(userDTO.toString());

        //存在保存用户信息到ThreadLocal
        UserHolder.saveUser(userDTO);
        log.info("保存信息到ThreadLocal"+ UserHolder.getUser());

        //刷新token的有效期
        stringRedisTemplate.expire(tokenKey,30, TimeUnit.MINUTES);
        log.info("刷新了token的有效期");

//        try {
//            JWTUtils.parseJWT(jwt);//解析JWT
//        }catch (Exception exception){
//            exception.printStackTrace();
//            Result e = Result.error("令牌解析失败");
//            log.info("令牌解析失败");
//            String s = JSONObject.toJSONString(e);
//            response.getWriter().write(s);
//            return false;
//        }
        //放行
        return true;
    }
}
