package com.example.starrail.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.starrail.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段填充");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", UserHolder.getUser().getId());
        metaObject.setValue("updateUser", UserHolder.getUser().getId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段填充");
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", UserHolder.getUser().getId());
    }
}
