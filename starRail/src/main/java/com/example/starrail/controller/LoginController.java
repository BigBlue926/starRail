package com.example.starrail.controller;

import com.example.starrail.common.Result;
import com.example.starrail.pojo.entity.User;
import com.example.starrail.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody User user, HttpSession session){
        return userService.login(user,session);
    }
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
        return userService.loginOut(request);
    }
}
