package com.example.starrail.service.serviceImpl;

import com.example.starrail.pojo.entity.Article;
import com.example.starrail.mapper.ArticleMapper;
import com.example.starrail.pojo.PageBean;
import com.example.starrail.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl  implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Override
    public PageBean page(Integer page, Integer pageSize) {
        //获取总记录数
        Long count = articleMapper.count();
        Integer start = (page - 1) * pageSize;
        //获取分页查询结果列表
        List<Article> ArticleList = articleMapper.page(start, pageSize);
        //封装对象
        PageBean pageBean = new PageBean(count,ArticleList);
        return pageBean;
    }
}
