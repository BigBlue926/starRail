package com.example.starrail.controller;

import com.example.starrail.common.Result;
import com.example.starrail.pojo.entity.GoodsCategory;
import com.example.starrail.service.GoodsCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/goods/category")
public class GoodsCategoryController {
    @Autowired
    GoodsCategoryService goodsCategoryService;
    @GetMapping("/list")
    public Result getCategoryList(GoodsCategory category){

        return goodsCategoryService.getCategoryList(category);
    }

    @DeleteMapping
    public Result delete(Integer id){
        log.info("删除商品分类" + id);
        return goodsCategoryService.remove(id);
    }
    @PutMapping
    public Result update(@RequestBody GoodsCategory category){
        log.info("更新的分类信息为："+ category.toString());
        goodsCategoryService.updateById(category);
        return Result.success("分类信息修改成功");
    }
    @PostMapping
    public Result save( @RequestBody GoodsCategory category){
        log.info("新增的分类信息为："+category.toString());
        goodsCategoryService.save(category);
        return Result.success("新增管理用户成功");
    }
}
