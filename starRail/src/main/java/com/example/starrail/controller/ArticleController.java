package com.example.starrail.controller;

import com.example.starrail.common.Result;
import com.example.starrail.pojo.PageBean;
import com.example.starrail.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @GetMapping("/article")
    //如果前端没有传递参数，设置默认值为1
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize){
        PageBean pageBean = articleService.page(page,pageSize);
        return Result.success(pageBean);
    }
}
