package com.flashlack.homeofesportsracingsimulatorsettings.config.Initializer;

import com.flashlack.homeofesportsracingsimulatorsettings.config.constant.UUIDInitializationConstants;
import com.flashlack.homeofesportsracingsimulatorsettings.until.UUIDUtils;
import com.xlf.utility.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author FLASHLACK
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class Initializer {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    @Order(2)
    public ApplicationRunner initializeData() {
        return args -> {
            // 初始化角色表、游戏、车辆、赛道和系统常量数据
            if (isTableDataInitialized("settings_role")
                    || isTableDataInitialized("settings_game")
                    || isTableDataInitialized("settings_car")
                    || isTableDataInitialized("settings_track")
                    || isTableDataInitialized("system_constants")) {
                initializeBaseData();
                log.info("数据库表内数据初始化完成！");
            } else {
                log.info("基础表中已有数据，跳过初始化操作。");
            }
            // 初始化管理员用户数据
            if (isTableDataInitialized("settings_user")) {
                initializeAdminUser();
                log.info("管理员用户数据初始化完成！");
            } else {
                log.info("用户表内已有数据，跳过初始化操作。");
            }
        };
    }

    // 初始化基础数据
    private void initializeBaseData() {
        UUIDUtils.addOccupiedUuid(UUIDInitializationConstants.ADMIN_ROLE_UUID);
        log.info("已占用的管理员角色 UUID: {}", UUIDInitializationConstants.ADMIN_ROLE_UUID);
        UUIDUtils.addOccupiedUuid(UUIDInitializationConstants.USER_ROLE_UUID);
        log.info("已占用的普通用户角色 UUID: {}", UUIDInitializationConstants.USER_ROLE_UUID);

        // 插入角色
        insertRole(UUIDInitializationConstants.ADMIN_ROLE_UUID, "管理员", "ALL_PERMISSIONS");
        insertRole(UUIDInitializationConstants.USER_ROLE_UUID, "普通用户", "VIEW, EDIT");

        // 插入游戏
        insertGame(UUIDInitializationConstants.GAME_ACC_UUID, "神力科莎:争锋");
        insertGame(UUIDInitializationConstants.GAME_F124_UUID, "F124");

        // 插入车辆
        insertCar(UUIDInitializationConstants.CAR_ACC_FERRARI296_UUID, "FERRARI296");
        insertCar(UUIDInitializationConstants.CAR_ACC_HGT3E2_UUID, "HGT3E2");
        insertCar(UUIDInitializationConstants.CAR_ACC_PORSCHE992R_UUID, "PORSCHE992R");
        insertCar(UUIDInitializationConstants.CAR_F124_F1_UUID, "F1");

        // 插入赛道
        insertTrack(UUIDInitializationConstants.TRACK_ACC_SPA_UUID, "ACC_SPA");
        insertTrack(UUIDInitializationConstants.TRACK_ACC_MONZA_UUID, "ACC_MONZA");
        insertTrack(UUIDInitializationConstants.TRACK_F124_SPA_UUID, "F124_SPA");
        insertTrack(UUIDInitializationConstants.TRACK_F124_MONZA_UUID, "F124_MONZA");

        // 插入系统常量
        insertSystemConstants("ADMIN_ROLE_UUID", UUIDInitializationConstants.ADMIN_ROLE_UUID);
        insertSystemConstants("USER_ROLE_UUID", UUIDInitializationConstants.USER_ROLE_UUID);
        insertSystemConstants("GAME_ACC_UUID", UUIDInitializationConstants.GAME_ACC_UUID);
        insertSystemConstants("GAME_F124_UUID", UUIDInitializationConstants.GAME_F124_UUID);
        insertSystemConstants("CAR_ACC_FERRARI296_UUID", UUIDInitializationConstants.CAR_ACC_FERRARI296_UUID);
        insertSystemConstants("CAR_ACC_HGT3E2_UUID", UUIDInitializationConstants.CAR_ACC_HGT3E2_UUID);
        insertSystemConstants("CAR_ACC_PORSCHE992R_UUID", UUIDInitializationConstants.CAR_ACC_PORSCHE992R_UUID);
        insertSystemConstants("CAR_F124_F1_UUID", UUIDInitializationConstants.CAR_F124_F1_UUID);
        insertSystemConstants("TRACK_ACC_SPA_UUID", UUIDInitializationConstants.TRACK_ACC_SPA_UUID);
        insertSystemConstants("TRACK_ACC_MONZA_UUID", UUIDInitializationConstants.TRACK_ACC_MONZA_UUID);
        insertSystemConstants("TRACK_F124_SPA_UUID", UUIDInitializationConstants.TRACK_F124_SPA_UUID);
        insertSystemConstants("TRACK_F124_MONZA_UUID", UUIDInitializationConstants.TRACK_F124_MONZA_UUID);
    }

    // 初始化管理员用户数据
    private void initializeAdminUser() {
        String adminUserUuid = UUIDUtils.generateUuid();
        UUIDUtils.addOccupiedUuid(adminUserUuid);
        log.info("已占用的管理员UUID为:{}", adminUserUuid);
        String sql = "INSERT INTO settings_user (user_uuid, role_uuid, user_email, user_password, nick_name) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, adminUserUuid, UUIDInitializationConstants.ADMIN_ROLE_UUID, "flashlack1314@163.com", PasswordUtil.encrypt("qwer1234"), "管理员用户示例");
        log.info("成功插入管理员用户：UUID = {}, 别名为 = {}, 邮箱 = {}，密码 = {}", adminUserUuid, "管理员用户示例", "flashlack1314@163.com", "qwer1234");
    }

    // 插入角色数据
    private void insertRole(String roleUuid, String roleAlias, String rolePermission) {
        try {
            String sql = "INSERT INTO settings_role (role_uuid, role_alias, role_permission) VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, roleUuid, roleAlias, rolePermission);
            log.info("成功插入角色：UUID = {}, 别名 = {}, 权限 = {}", roleUuid, roleAlias, rolePermission);
        } catch (Exception e) {
            log.error("插入角色失败：UUID = {}, 别名 = {}, 错误信息 = {}", roleUuid, roleAlias, e.getMessage());
        }
    }

    // 插入游戏数据
    private void insertGame(String gameUuid, String gameName) {
        try {
            UUIDUtils.addOccupiedUuid(gameUuid);
            String sql = "INSERT INTO settings_game (game_uuid, game_name) VALUES (?, ?)";
            jdbcTemplate.update(sql, gameUuid, gameName);
            log.info("成功插入游戏：UUID = {}, 名称 = {}", gameUuid, gameName);
        } catch (Exception e) {
            log.error("插入游戏失败：UUID = {}, 名称 = {}, 错误信息 = {}", gameUuid, gameName, e.getMessage());
        }
    }

    // 插入车辆数据
    private void insertCar(String carUuid, String carName) {
        try {
            UUIDUtils.addOccupiedUuid(carUuid);
            String sql = "INSERT INTO settings_car (car_uuid, car_name) VALUES (?, ?)";
            jdbcTemplate.update(sql, carUuid, carName);
            log.info("成功插入车辆：UUID = {}, 名称 = {}", carUuid, carName);
        } catch (Exception e) {
            log.error("插入车辆失败：UUID = {}, 名称 = {}, 错误信息 = {}", carUuid, carName, e.getMessage());
        }
    }

    // 插入赛道数据
    private void insertTrack(String trackUuid, String trackName) {
        try {
            UUIDUtils.addOccupiedUuid(trackUuid);
            String sql = "INSERT INTO settings_track (track_uuid, track_name) VALUES (?, ?)";
            jdbcTemplate.update(sql, trackUuid, trackName);
            log.info("成功插入赛道：UUID = {}, 名称 = {}", trackUuid, trackName);
        } catch (Exception e) {
            log.error("插入赛道失败：UUID = {}, 名称 = {}, 错误信息 = {}", trackUuid, trackName, e.getMessage());
        }
    }

    // 插入系统常量数据
    private void insertSystemConstants(String key, String value) {
        try {
            String sql = "INSERT INTO system_constants (key, value) VALUES (?, ?)";
            jdbcTemplate.update(sql, key, value);
            log.info("成功插入系统常量：key = {}, value = {}", key, value);
        } catch (Exception e) {
            log.error("插入系统常量失败：key = {}, value = {}, 错误信息 = {}", key, value, e.getMessage());
        }
    }

    // 检查表是否有数据
    private boolean isTableDataInitialized(String tableName) {
        try {
            String checkDataSql = String.format("SELECT COUNT(*) FROM %s", tableName);
            Integer count = jdbcTemplate.queryForObject(checkDataSql, Integer.class);
            log.info("表 [{}] 中已有数据条数: {}", tableName, count);
            return count == null || count == 0;
        } catch (Exception e) {
            log.error("检查表 [{}] 中数据时出错：{}", tableName, e.getMessage());
            return false;
        }
    }
}