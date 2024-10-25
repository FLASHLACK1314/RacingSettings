package com.flashlack.homeofesportsracingsimulatorsettings.config.constant;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.SystemConstantsDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于存放系统常量的UUID方法
 *
 * @author FLASHLAKC
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class UUIDConstants {
    private static final Map<String, String> CONSTANTS_MAP = new HashMap<>();

    private final SystemConstantsDAO systemConstantsDAO;

    @Bean
    @Order(1000)
    public CommandLineRunner loadUuidConstants() {
        return args -> {
            // 从数据库加载每个 UUID 值
            CONSTANTS_MAP.put("ADMIN_ROLE_UUID", systemConstantsDAO.getById("ADMIN_ROLE_UUID").getValue());
            CONSTANTS_MAP.put("USER_ROLE_UUID", systemConstantsDAO.getById("USER_ROLE_UUID").getValue());
            CONSTANTS_MAP.put("GAME_ACC_UUID", systemConstantsDAO.getById("GAME_ACC_UUID").getValue());
            CONSTANTS_MAP.put("GAME_F124_UUID", systemConstantsDAO.getById("GAME_F124_UUID").getValue());
            CONSTANTS_MAP.put("CAR_ACC_FERRARI296_UUID", systemConstantsDAO.getById("CAR_ACC_FERRARI296_UUID").getValue());
            CONSTANTS_MAP.put("CAR_ACC_HGT3E2_UUID", systemConstantsDAO.getById("CAR_ACC_HGT3E2_UUID").getValue());
            CONSTANTS_MAP.put("CAR_ACC_PORSCHE992R_UUID", systemConstantsDAO.getById("CAR_ACC_PORSCHE992R_UUID").getValue());
            CONSTANTS_MAP.put("CAR_F124_F1_UUID", systemConstantsDAO.getById("CAR_F124_F1_UUID").getValue());
            CONSTANTS_MAP.put("TRACK_ACC_SPA_UUID", systemConstantsDAO.getById("TRACK_ACC_SPA_UUID").getValue());
            CONSTANTS_MAP.put("TRACK_ACC_MONZA_UUID", systemConstantsDAO.getById("TRACK_ACC_MONZA_UUID").getValue());
            CONSTANTS_MAP.put("TRACK_F124_SPA_UUID", systemConstantsDAO.getById("TRACK_F124_SPA_UUID").getValue());
            CONSTANTS_MAP.put("TRACK_F124_MONZA_UUID", systemConstantsDAO.getById("TRACK_F124_MONZA_UUID").getValue());
        };
    }

    // 静态方法获取常量
    public static String getConstant(String key) {
        return CONSTANTS_MAP.get(key);
    }
}