package com.flashlack.homeofesportsracingsimulatorsettings.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author 26473
 */
@Component
@Slf4j
public class CustomMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("正在进行插入操作的自动填充...");
        // 插入时填充创建时间
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());

        // 插入时填充过期时间（当前时间 + 5 分钟）
        this.strictInsertFill(metaObject, "expireTime", LocalDateTime.class, LocalDateTime.now().plusMinutes(5));

        log.info("插入时自动填充完成：createTime={}, expireTime={}", LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("正在进行更新操作的自动填充...");
        // 更新时填充更新时间
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        log.info("更新时自动填充完成：updateTime={}", LocalDateTime.now());
    }
}