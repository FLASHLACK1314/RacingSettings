package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.UUIDConstants;
import com.flashlack.homeofesportsracingsimulatorsettings.until.UUIDUtils;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 初始化控制器
 *
 * @author FLASHLACK
 */
@RestController
@RequestMapping("v1/initializer")
@RequiredArgsConstructor
@Slf4j
public class InitializerController {
    private final JdbcTemplate jdbcTemplate;
    /**
     * 初始化角色表和管理员用户表
     *
     * @return 初始化结果信息
     */
    @GetMapping("/data")
    public @NotNull ResponseEntity<BaseResponse<String>> initializeRoleAndAdminUserData() {
        // 初始化角色表中的数据
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
        return ResultUtil.success("初始化成功", "初始化成功");
    }
    /**
     * 初始化 基础表表中的数据
     */
    private void initializeBaseData() {
        // 使用 UUIDUtils 管理 UUID，并防止重复
        UUIDUtils.addOccupiedUuid(UUIDConstants.ADMIN_ROLE_UUID);
        log.info("已占用的管理员角色 UUID: {}", UUIDConstants.ADMIN_ROLE_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.USER_ROLE_UUID);
        log.info("已占用的普通用户角色 UUID: {}", UUIDConstants.USER_ROLE_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.GAME_ACC_UUID);
        log.info("已占用的游戏ACC的UUID: {}", UUIDConstants.GAME_ACC_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.GAME_F124_UUID);
        log.info("已占用的游戏F124的UUID: {}", UUIDConstants.GAME_F124_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.CAR_ACC_FERRARI296_UUID);
        log.info("已占用的车辆ACC_FERRARI296的UUID: {}", UUIDConstants.CAR_ACC_FERRARI296_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.CAR_ACC_HGT3E2_UUID);
        log.info("已占用的车辆ACC_HGT3E2的UUID: {}", UUIDConstants.CAR_ACC_HGT3E2_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.CAR_F124_F1_UUID);
        log.info("已占用的车辆F124_F1的UUID: {}", UUIDConstants.CAR_F124_F1_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.TRACK_ACC_SPA_UUID);
        log.info("已占用的赛道ACC_SPA赛道UUID: {}", UUIDConstants.TRACK_ACC_SPA_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.TRACK_ACC_MONZA_UUID);
        log.info("已占用的赛道ACC_MONZA赛道UUID: {}", UUIDConstants.TRACK_ACC_MONZA_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.TRACK_F124_SPA_UUID);
        log.info("已占用的赛道F124_SPA赛道UUID: {}", UUIDConstants.TRACK_F124_SPA_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.TRACK_F124_MONZA_UUID);
        log.info("已占用的赛道F124_MONZA赛道UUID: {}", UUIDConstants.TRACK_F124_MONZA_UUID);
        // 插入管理员角色
        insertRole(UUIDConstants.ADMIN_ROLE_UUID, "管理员", "ALL_PERMISSIONS");
        // 插入普通用户角色
        insertRole(UUIDConstants.USER_ROLE_UUID, "普通用户", "VIEW, EDIT");
        // 插入游戏
        insertGame(UUIDConstants.GAME_ACC_UUID, "神力科莎:争锋");
        insertGame(UUIDConstants.GAME_F124_UUID, "F124");
        // 插入车辆
        insertCar(UUIDConstants.CAR_ACC_FERRARI296_UUID, "FERRARI296");
        insertCar(UUIDConstants.CAR_ACC_HGT3E2_UUID, "HGT3E2");
        insertCar(UUIDConstants.CAR_F124_F1_UUID, "F1");
        // 插入赛道
        insertTrack(UUIDConstants.TRACK_ACC_SPA_UUID, "ACC_SPA");
        insertTrack(UUIDConstants.TRACK_ACC_MONZA_UUID, "ACC_MONZA");
        insertTrack(UUIDConstants.TRACK_F124_SPA_UUID, "F124_SPA");
        insertTrack(UUIDConstants.TRACK_F124_MONZA_UUID, "F124_MONZA");
        //插入系统常量值
        insertSystemConstants("ADMIN_ROLE_UUID", UUIDConstants.ADMIN_ROLE_UUID);
        insertSystemConstants("USER_ROLE_UUID", UUIDConstants.USER_ROLE_UUID);
        insertSystemConstants("GAME_ACC_UUID", UUIDConstants.GAME_ACC_UUID);
        insertSystemConstants("CAR_ACC_UUID", UUIDConstants.CAR_ACC_FERRARI296_UUID);
        insertSystemConstants("GAME_CAR_TRACK_ACC_UUID", UUIDConstants.GAME_CAR_TRACK_ACC_UUID);
    }
    /**
     * 初始化管理员用户数据
     */
    private void initializeAdminUser() {
        String adminUserUuid = UUIDUtils.generateUuid();
        UUIDUtils.addOccupiedUuid(adminUserUuid);
        log.info("已占用的管理员UUID为:{}", adminUserUuid);
        String sql = "INSERT INTO settings_user (user_uuid, role_uuid,user_email, user_password, nick_name) " +
                "VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, adminUserUuid, UUIDConstants.ADMIN_ROLE_UUID, "flashlack1314@163.com", "qwer1234", "管理员用户示例");
        log.info("成功插入管理员用户：UUID = {}, 别名为 = {}, 邮箱 = {}，密码 = {}", adminUserUuid, "管理员用户示例", "flashlack1314@163.com", "qwer1234");
    }
    /**
     * 插入角色数据到 `settings_role` 表
     *
     * @param roleUuid       角色 UUID
     * @param roleAlias      角色别名
     * @param rolePermission 角色权限
     */
    private void insertRole(String roleUuid, String roleAlias, String rolePermission) {
        try {
            String sql = "INSERT INTO settings_role (role_uuid, role_alias, role_permission) " +
                    "VALUES (?, ?, ?)";
            jdbcTemplate.update(sql, roleUuid, roleAlias, rolePermission);
            log.info("成功插入角色：UUID = {}, 别名 = {}, 权限 = {}", roleUuid, roleAlias, rolePermission);
        } catch (Exception e) {
            log.error("插入角色失败：UUID = {}, 别名 = {}, 错误信息 = {}", roleUuid, roleAlias, e.getMessage());
        }
    }

    /**
     * 插入游戏数据到 `settings_game` 表
     * @param gameUuid UUID
     * @param gameName 游戏名称
     */
    private void insertGame(String gameUuid, String gameName){
        try {
            String sql = "INSERT INTO settings_game (game_uuid,game_name)"+
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, gameUuid, gameName);
            log.info("成功插入游戏：UUID = {}, 名称 = {}", gameUuid, gameName);
        } catch (Exception e) {
            log.error("插入游戏失败：UUID = {}, 名称 = {}, 错误信息 = {}", gameUuid, gameName, e.getMessage());
        }
    }

    /**
     * 插入车辆数据到 `settings_car` 表
     * @param carUuid UUID
     * @param carName 车辆名称
     */
    private void insertCar(String carUuid,String carName){
        try {
            String sql = "INSERT INTO settings_car (car_uuid,car_name)"+
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, carUuid, carName);
            log.info("成功插入车辆：UUID = {}, 名称 = {}", carUuid, carName);
        } catch (Exception e) {
            log.error("插入车辆失败：UUID = {}, 名称 = {}, 错误信息 = {}", carUuid, carName, e.getMessage());
        }
    }

    /**
     * 插入赛道数据到 `settings_track` 表
     * @param trackUuid UUID
     * @param trackName 赛道名称
     */
    private void insertTrack(String trackUuid,String trackName){
        try {
            String sql = "INSERT INTO settings_track (track_uuid,track_name)"+
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, trackUuid, trackName);
            log.info("成功插入赛道：UUID = {}, 名称 = {}", trackUuid, trackName);
        } catch (Exception e) {
            log.error("插入赛道失败：UUID = {}, 名称 = {}, 错误信息 = {}", trackUuid, trackName, e.getMessage());
        }
    }
    /**
     * 插入系统常量
     *
     * @param key   系统常量的键
     * @param value 系统常量的值
     */
    private void insertSystemConstants(String key, String value){
        try {
            String sql = "INSERT INTO system_constants (key, value) " +
                    "VALUES (?, ?)";
            jdbcTemplate.update(sql, key, value);
            log.info("成功插入系统常量：key = {}, value = {}", key, value);
        } catch (Exception e) {
            log.error("插入系统常量失败：key = {}, value = {}, 错误信息 = {}", key, value, e.getMessage());
        }
    }
    /**
     * 检查指定表中是否已有数据
     *
     * @param tableName 要检查的表名
     * @return 如果表中已有数据，则返回 true；否则返回 false
     */
    private boolean isTableDataInitialized(String tableName) {
        try {
            String checkDataSql = String.format("SELECT COUNT(*) FROM %s", tableName);
            Integer count = jdbcTemplate.queryForObject(checkDataSql, Integer.class);
            boolean isInitialized = count != null && count > 0;
            log.info("表 [{}] 中已存在的数据条数: {}", tableName, count);
            return !isInitialized;
        } catch (Exception e) {
            log.error("检查表 [{}] 中数据时出错：{}", tableName, e.getMessage());
            return true;
        }
    }

}

