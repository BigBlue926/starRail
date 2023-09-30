package com.example.starrail.controller;


import com.example.starrail.common.Result;

import com.example.starrail.pojo.DTO.GoodsDTO;
import com.example.starrail.pojo.entity.Goods;
import com.example.starrail.pojo.entity.GoodsCategory;
import com.example.starrail.pojo.entity.GoodsDetails;
import com.example.starrail.service.GoodsCategoryService;
import com.example.starrail.service.GoodsDetailsService;
import com.example.starrail.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsDetailsService goodsDetailsService;

    @GetMapping("/getGoods")
    public Result getGoods(@RequestParam int page, int pageSize, Integer categoryId,String name){
        return goodsService.getGoods(page,pageSize,categoryId,name);
    }
    @PostMapping
    public Result save(@RequestBody GoodsDTO goodsDTO){
        log.info("GoodsDTO:" + goodsDTO.toString());
        goodsService.saveWithGoodsType(goodsDTO);
        return Result.success("添加商品成功");
    }
    @GetMapping("/details/{id}")
    public Result goodsDetails(@PathVariable Integer id){
        return goodsDetailsService.selectById(id);
    }
    @PutMapping("/details")
    public Result update(@RequestBody GoodsDetails details){
        log.info("更新的商品细节信息为："+ details.toString());
        return goodsDetailsService.detailsUpdate(details);
    }
    @PutMapping
    public Result update(@RequestBody Goods goods){
        return goodsService.update(goods);
    }
    @DeleteMapping
    public Result delete(Integer id){
        log.info("删除商品" + id);
        return goodsService.remove(id);
    }
    @DeleteMapping("/details")
    public Result detailsDelete(Integer id){
        log.info("删除商品细节" + id);
        goodsDetailsService.removeById(id);
        return Result.success("商品细节删除成功");
    }

}
