package com.example.starrail.service.serviceImpl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.starrail.common.Result;
import com.example.starrail.mapper.GoodsDetailsMapper;
import com.example.starrail.pojo.entity.GoodsDetails;
import com.example.starrail.service.GoodsDetailsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java.util.concurrent.TimeUnit;

import static com.example.starrail.utils.RedisConstants.GOODS_DETAILS_KEY;

@Slf4j
@Service
public class GoodDetailsServiceImpl extends ServiceImpl<GoodsDetailsMapper, GoodsDetails> implements GoodsDetailsService {
    @Autowired
    GoodsDetailsMapper goodsDetailsMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public Result selectById(Integer id) {
        if(id == null){
            return Result.error("查询商品id为空");
        }
        log.info("查询商品细节");
        String key = GOODS_DETAILS_KEY + id;
        String detailsJson = stringRedisTemplate.opsForValue().get(key);
        //存在直接返回
        if(StrUtil.isNotBlank(detailsJson)){
            log.info("缓存命中");
            List<GoodsDetails> list = JSONUtil.toList(detailsJson,GoodsDetails.class);
            return Result.success(list);
        }
        //缓存穿透
        //判断缓存是否为null
        if(detailsJson != null){
            return Result.error("信息不存在");
        }
        LambdaQueryWrapper<GoodsDetails> queryWrapper = new LambdaQueryWrapper<GoodsDetails>();
        queryWrapper.eq(GoodsDetails::getGoodsId, id);
        List<GoodsDetails> list = goodsDetailsMapper.selectList(queryWrapper);
        if(list.isEmpty()){
            stringRedisTemplate.opsForValue().set(key,"", 2L,TimeUnit.MINUTES);
            return Result.error("商品细节不存在");
        }
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(list),30L, TimeUnit.MINUTES);

        return Result.success(list);
    }

    @Override
    @Transactional
    public Result detailsUpdate(GoodsDetails details) {
        Integer id = details.getId();
        if(id == null){
            Result.error("商品细节id为空");
        }
        //更新数据库和删除缓存6
        updateById(details);
        String key = GOODS_DETAILS_KEY + id;
        stringRedisTemplate.delete(key);
        return Result.success("商品细节信息修改成功");
    }

}
