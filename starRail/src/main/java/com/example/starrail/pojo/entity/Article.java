package com.example.starrail.pojo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Article {
    Integer UserId;//作者id
    Integer category;//文章分类
    String ArticleCover;//文章缩略图
    String ArticleTitle;//文章标题
    String ArticleContent;//文章内容
    Integer IsTop;//是否顶置
    Integer IsFeatured;//是否推荐
    Integer IsDelete;//是否删除
    Integer ArticleStatus;//状态值，公开私密草稿
    Integer Type;//文章类型 原创转载
    String ArticlePassword;//访问密码
    String OriginalUrl;//原文链接
    Date CreateTime;//发表时间
    Date UpdateTime;//更新时间

}
