package com.example.starrail.service;

import com.example.starrail.pojo.PageBean;

public interface ArticleService  {
    PageBean page(Integer page, Integer pageSize);
}
