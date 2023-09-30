package com.example.starrail.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.starrail.common.CustomException;
import com.example.starrail.common.Result;
import com.example.starrail.mapper.GoodsCategoryMapper;
import com.example.starrail.pojo.entity.Goods;
import com.example.starrail.pojo.entity.GoodsCategory;
import com.example.starrail.service.GoodsCategoryService;
import com.example.starrail.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements GoodsCategoryService {
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @Override
    public Result getCategoryList(GoodsCategory category) {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategory::getTypeId,category.getTypeId());
        queryWrapper.orderByAsc(GoodsCategory::getSort).orderByAsc(GoodsCategory::getUpdateTime);
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.selectList(queryWrapper);
        return Result.success(goodsCategories);
    }

    @Override
    public Result remove(Integer id) {
        if(id == null){
            return Result.error("分类不存在");
        }
        //看当前分类下是否有商品
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<Goods>();
        queryWrapper.eq(Goods::getCategoryId,id);
        int goodsCount = (int) goodsService.count(queryWrapper);
        if(goodsCount > 0){
            throw new CustomException("当前分类下有商品，不能删除");
        }
        super.removeById(id);
        return Result.success("删除分类成功");
    }
}
