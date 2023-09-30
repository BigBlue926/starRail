package com.example.starrail.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.starrail.common.Result;
import com.example.starrail.pojo.DTO.UserDTO;
import com.example.starrail.pojo.entity.Menu;
import com.example.starrail.pojo.entity.User;
import com.example.starrail.mapper.UserMapper;
import com.example.starrail.service.UserService;
import com.example.starrail.utils.JWTUtils;
import com.example.starrail.utils.UserHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.example.starrail.utils.RedisConstants.LOGIN_USER_KEY;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;
    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    public Result login(User user, HttpSession session) {
        String password = user.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        String username = user.getUsername();

//        User u = userMapper.selectByUsernameAndPassword(user);
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        User u = userMapper.selectOne(queryWrapper);
        log.info("查询到的为:"+ u.toString());
        if (u == null) {
            log.info("用户不存在");
            return Result.error("用户或密码错误");
        }
        if (u.getUsername().equals(username) && u.getPassword().equals(password)){
            Map<String, Object> claims = new HashMap<>();
            claims.put("username",u.getUsername());
            claims.put("userId", u.getId());
            String jwt = JWTUtils.generateJwt(claims);
            String tokenKey = LOGIN_USER_KEY + jwt;
            UserDTO userDTO = BeanUtil.copyProperties(u, UserDTO.class);
            log.info("userDT"+userDTO.toString());
            Map<String, Object> userMap = BeanUtil.beanToMap(userDTO,new HashMap<>(),
                    //解决属性不能强转以及空指针问题
                    CopyOptions.create().
                            setIgnoreNullValue(true)
                            //该方法只能解决不能强转问题
                            //.setFieldValueEditor((fieldName,fieldValue) -> fieldValue.toString()));

                            //解决方法：在setFieldValueEditor中也需要判空
                            .setFieldValueEditor((fieldName,fieldValue) -> {
                                if (fieldValue == null){
                                    fieldValue = "0";
                                }else {
                                    fieldValue = fieldValue.toString();
                                }
                                return fieldValue;
                            }) );
            log.info((String) userMap.toString());
            //redis存储
            stringRedisTemplate.opsForHash().putAll(tokenKey,userMap);
            //设置有效期
            stringRedisTemplate.expire(tokenKey,30, TimeUnit.MINUTES);
            claims.put("token",jwt);
            log.info(Result.success(claims).toString());
            return Result.LoginSuccess(claims);
        }
        return Result.error("密码错误");
    }

    @Override
    public Result loginOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserDTO userDTO = UserHolder.getUser();
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().delete(tokenKey);
        //移除ThreadLocal
        UserHolder.removeUser();
        log.info("退出登录成功");
        return Result.success("退出登录成功");
    }

    @Override
    public Result getUserMenu(HttpSession session) {
        if(session == null || session.getAttribute("user") == null){
            return Result.error("登录过期");
        }
        else {
            User user = (User) session.getAttribute("user");
            Integer id = user.getId();
            Integer RoleId = userMapper.getUserRoleId(id);
            List<Menu> userMenu = userMapper.getUserMenu(RoleId);
            return Result.success(userMenu);
        }
    }


}
