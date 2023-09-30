package com.example.starrail.controller;

import com.example.starrail.common.Result;
import com.example.starrail.pojo.entity.User;
import com.example.starrail.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/menu")
    public Result getUserMenu (HttpSession session){
        return userService.getUserMenu(session);
    }
    @PostMapping("/loginOut")
    public Result loginOut(HttpServletRequest request){
        return userService.loginOut(request);
    }
    @PostMapping
    public Result save(HttpSession session,@RequestBody User user){
        log.info("新增的用户信息为："+user.toString());
        //设置默认初始密码
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        user.setCreateTime(LocalDateTime.now());
//        user.setUpdateTime(LocalDateTime.now());
//        //获取当前用户的id
////        Integer userId = (Integer) session.getAttribute("user");
//        Integer userId = 2;
//        user.setCreateUser(userId);
//        user.setUpdateUser(userId);
        userService.save(user);
        return Result.success("新增管理用户成功");
    }
    @GetMapping("/{id}")
    public  Result getById(@PathVariable Integer id){
        log.info("查询用户信息...");
        User user =  userService.getById(id);
        log.info("查询到的用户信息：" + user.toString());
        return Result.success(user);
    }
    @PutMapping
    public Result update(@RequestBody User user){
        log.info("更新的用户信息为："+ user.toString());
        userService.updateById(user);
        return Result.success("用户信息修改成功");
    }

}
