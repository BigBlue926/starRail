package com.example.starrail.pojo.DTO;

import com.example.starrail.pojo.entity.Goods;
import com.example.starrail.pojo.entity.GoodsDetails;
import lombok.Data;

import java.util.List;

@Data
public class GoodsDTO extends Goods {
    private List<GoodsDetails> goodsDetails;

}
