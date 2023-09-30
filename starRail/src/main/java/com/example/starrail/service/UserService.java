package com.example.starrail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.starrail.common.Result;
import com.example.starrail.pojo.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public interface UserService extends IService<User> {
    Result loginOut(HttpServletRequest request);

    Result login(User user, HttpSession session);


    Result getUserMenu(HttpSession session);

}
