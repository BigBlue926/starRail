package com.example.starrail.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;

@Data
public class GoodsCategory {
    @TableId(type = IdType.ASSIGN_ID)
    Integer id;
    String name;
    Integer typeId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    Integer isDeleted;
    Integer sort;
}
