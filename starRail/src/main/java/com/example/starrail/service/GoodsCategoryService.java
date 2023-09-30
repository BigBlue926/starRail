package com.example.starrail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.starrail.common.Result;
import com.example.starrail.pojo.entity.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService extends IService<GoodsCategory> {
    Result<List<GoodsCategory>> getCategoryList(GoodsCategory category);

    Result remove(Integer id);
}
