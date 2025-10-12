package com.flashlack.homeofesportsracingsimulatorsettings.config.Initializer;

import com.flashlack.homeofesportsracingsimulatorsettings.config.constant.UUIDInitializationConstants;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
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
            // 初始化角色表数据
            if (isTableDataInitialized("settings_role")) {
                initializeBaseData();
                log.info("数据库表内数据初始化完成！");
            } else {
                log.info("角色表中已有数据，跳过初始化操作。");
            }
            // 初始化管理员用户数据
            if (isTableDataInitialized("settings_user")) {
                initializeAdminUser();
                log.info("管理员用户数据初始化完成！");
            } else {
                log.info("用户表内已有数据，跳过初始化操作，所有数据已经初始化完成。");
            }
        };
    }

    // 初始化基础数据
    private void initializeBaseData() {
        UUIDUtils.addOccupiedUuid(UUIDInitializationConstants.ADMIN_ROLE_UUID);
        log.info("已占用的管理员角色 UUID: {}", UUIDInitializationConstants.ADMIN_ROLE_UUID);
        UUIDUtils.addOccupiedUuid(UUIDInitializationConstants.USER_ROLE_UUID);
        log.info("已占用的普通用户角色 UUID: {}", UUIDInitializationConstants.USER_ROLE_UUID);
        UUIDUtils.addOccupiedUuid(UUIDInitializationConstants.ORGANIZER_ROLE_UUID);
        log.info("已占用的比赛主办方角色 UUID: {}", UUIDInitializationConstants.ORGANIZER_ROLE_UUID);

        // 插入角色
        insertRole(UUIDInitializationConstants.ADMIN_ROLE_UUID, "管理员", "ALL_PERMISSIONS");
        insertRole(UUIDInitializationConstants.USER_ROLE_UUID, "参赛者", "VIEW, EDIT");
        insertRole(UUIDInitializationConstants.ORGANIZER_ROLE_UUID, "主办方", "MANAGE_MATCH");
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
