package com.example.starrail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.starrail.common.Result;
import com.example.starrail.pojo.DTO.GoodsDTO;
import com.example.starrail.pojo.entity.Goods;


public interface GoodsService  extends IService<Goods> {
     Result getGoods(int page, int pageSize, Integer categoryId,String name);

     void saveWithGoodsType(GoodsDTO goodsDTO);

    Result remove(Integer id);

    Result update(Goods goods);
}
