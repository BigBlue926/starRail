package com.example.starrail.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.starrail.common.Result;
import com.example.starrail.pojo.entity.GoodsDetails;


public interface GoodsDetailsService extends IService<GoodsDetails> {
    Result selectById(Integer id);

    Result detailsUpdate(GoodsDetails details);
}
