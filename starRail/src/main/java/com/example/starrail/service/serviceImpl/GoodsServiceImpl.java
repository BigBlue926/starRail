package com.example.starrail.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.starrail.common.CustomException;
import com.example.starrail.common.Result;
import com.example.starrail.mapper.GoodsMapper;
import com.example.starrail.pojo.DTO.GoodsDTO;
import com.example.starrail.pojo.entity.Goods;
import com.example.starrail.pojo.entity.GoodsDetails;
import com.example.starrail.service.GoodsService;
import com.example.starrail.service.GoodsDetailsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.starrail.utils.RedisConstants.GOODS_DETAILS;
import static com.example.starrail.utils.RedisConstants.GOODS_KEY;

@Slf4j
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper,Goods>  implements GoodsService{
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    private GoodsDetailsService goodsDetailsService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public Result getGoods(int page, int pageSize, Integer categoryId,String name) {
        Page<Goods> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),Goods::getName,name);
        queryWrapper.orderByDesc(Goods::getUpdateTime);
        Page<Goods> goodsPage = goodsMapper.selectPage(pageInfo, queryWrapper);
        log.info("page:"+goodsPage.toString());
        return Result.success(goodsPage);
    }

    @Transactional
    @Override
    public void saveWithGoodsType(GoodsDTO goodsDTO) {
        this.save(goodsDTO);

        //同时保存到GoodsDetails
        //MP在保存时候给id赋值回填了
        Integer goodsId = goodsDTO.getId();//商品id
        List<GoodsDetails> goodsDetailsList = goodsDTO.getGoodsDetails();
        goodsDetailsList = goodsDetailsList.stream().map((item) -> {
            item.setGoodsId(goodsId);;
            return item;
        }).collect(Collectors.toList());

        goodsDetailsService.saveBatch(goodsDetailsList);
    }

    @Override
    public Result remove(Integer id) {
        if(id == null){
            return Result.error("分类不存在");
        }
        LambdaQueryWrapper<GoodsDetails> queryWrapper = new LambdaQueryWrapper<GoodsDetails>();
        queryWrapper.eq(GoodsDetails::getGoodsId,id);
        //看当前商品下是否有款式
        long count = goodsDetailsService.count(queryWrapper);
        if(count > 0){
            throw new CustomException("当前商品下有很多款式，不能删除");
        }
        super.removeById(id);
        return Result.success("删除商品成功");
    }

    @Override
    public Result update(Goods goods) {
        log.info("更新的商品信息为："+ goods.toString());
        Integer id = goods.getId();
        if(id == null){
            Result.error("商品细节id为空");
        }
        //更新数据库和删除缓存6
        updateById(goods);
        String key = GOODS_KEY + id;
        stringRedisTemplate.delete(key);
        return Result.success("商品信息修改成功");
    }


}
