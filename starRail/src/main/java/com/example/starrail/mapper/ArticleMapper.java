package com.example.starrail.mapper;

import com.example.starrail.pojo.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper {
    @Select("select count(1) from article")
    public Long count();
    @Select("select * from article limit #{start},#{pageSize}")
    public List<Article> page(Integer start,Integer pageSize);
}
