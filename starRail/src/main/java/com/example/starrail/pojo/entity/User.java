package com.example.starrail.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    Integer id;//用户id
    String username;//账号
    String password;//密码
    Integer status;//用户状态
    Integer roleId;//角色id
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    Integer createUser;//创建人id
    @TableField(fill = FieldFill.INSERT_UPDATE)
    Integer updateUser;//更新人id
}
