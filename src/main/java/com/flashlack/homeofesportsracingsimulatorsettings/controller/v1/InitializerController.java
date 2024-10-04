package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.UUIDConstants;
import com.flashlack.homeofesportsracingsimulatorsettings.until.UUIDUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public String initializeRoleAndAdminUserData() {
        StringBuilder resultMessage = new StringBuilder();

        // 初始化角色表中的数据
        if (isTableDataInitialized("settings_role")) {
            initializeRoleData();
            resultMessage.append("角色表数据初始化完成！\n");
        } else {
            log.info("角色表中已有数据，跳过初始化操作。");
            resultMessage.append("角色表中已有数据，跳过初始化操作。\n");
        }
        // 初始化管理员用户数据
        if (isTableDataInitialized("settings_user")) {
            initializeAdminUser();
            resultMessage.append("管理员用户初始化完成！");
        } else {
            log.info("用户表内已有数据，跳过初始化操作。");
            resultMessage.append("用户表内已有角色，跳过初始化操作。");
        }


        return resultMessage.toString();
    }
    /**
     * 初始化 `settings_role` 表中的角色数据
     */
    private void initializeRoleData() {
        // 使用 UUIDUtils 管理 UUID，并防止重复
        UUIDUtils.addOccupiedUuid(UUIDConstants.ADMIN_ROLE_UUID);
        log.info("已占用的管理员角色 UUID: {}", UUIDConstants.ADMIN_ROLE_UUID);
        UUIDUtils.addOccupiedUuid(UUIDConstants.USER_ROLE_UUID);
        log.info("已占用的普通用户角色 UUID: {}", UUIDConstants.USER_ROLE_UUID);

        // 插入管理员角色
        insertRole(UUIDConstants.ADMIN_ROLE_UUID, "管理员", "ALL_PERMISSIONS");
        // 插入普通用户角色
        insertRole(UUIDConstants.USER_ROLE_UUID, "普通用户", "VIEW, EDIT");
        //插入系统常量值
        insertSystemConstants("ADMIN_ROLE_UUID", UUIDConstants.ADMIN_ROLE_UUID);
        insertSystemConstants("USER_ROLE_UUID", UUIDConstants.USER_ROLE_UUID);
        log.info("角色表数据初始化完成！");
    }
    /**
     * 初始化管理员用户数据
     */
    private void initializeAdminUser() {
        String adminUserUuid = UUIDUtils.generateUuid();
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

