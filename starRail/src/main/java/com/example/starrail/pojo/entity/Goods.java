package com.example.starrail.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Goods {
    @TableId(type = IdType.ASSIGN_ID)
    Integer id;
    String name;//款式名字
    Integer categoryId;//商品分类id
    Integer price;
    String png;//封面
    String description;//商品介绍
    Integer status;//销售状态
    Integer sort;//排序
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    Integer createUser;//创建人id
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Integer updateUser;//更新人id
    Integer isDeleted;//是否删除
}
