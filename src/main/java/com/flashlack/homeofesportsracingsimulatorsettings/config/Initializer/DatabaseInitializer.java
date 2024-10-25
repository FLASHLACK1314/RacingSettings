package com.flashlack.homeofesportsracingsimulatorsettings.config.Initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库初始化器
 * 确保表按照指定顺序创建
 *
 * @author FLASHLACK
 */
@Slf4j
@Configuration
public class DatabaseInitializer {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    // 使用 LinkedHashMap 来确保顺序
    private final Map<String, String> tableSqlMap = new LinkedHashMap<>();

    /**
     * 初始化表名及 SQL 文件路径映射关系
     */
    public DatabaseInitializer(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;

        // 使用 LinkedHashMap 来保证顺序
        tableSqlMap.put("settings_role", "templates/settings_role.sql");
        tableSqlMap.put("settings_user", "templates/settings_user.sql");
        tableSqlMap.put("email_code", "templates/email_code.sql");
        tableSqlMap.put("settings_game", "templates/settings_game.sql");
        tableSqlMap.put("settings_car", "templates/settings_car.sql");
        tableSqlMap.put("settings_track", "templates/settings_track.sql");
        tableSqlMap.put("settings_game_car_track", "templates/settings_game_car_track.sql");
        tableSqlMap.put("acc_setups", "templates/acc_setups.sql");
        tableSqlMap.put("f1_setups", "templates/f1_setups.sql");
        tableSqlMap.put("system_constants", "templates/system_constants.sql");
    }

    @Bean
    @Order(1)
    public CommandLineRunner run() {
        return args -> {
            // 遍历表名及对应的 SQL 文件路径，按照插入顺序执行
            for (Map.Entry<String, String> entry : tableSqlMap.entrySet()) {
                String tableName = entry.getKey();
                String scriptPath = entry.getValue();
                // 检查表是否存在
                if (!checkTableExists(tableName)) {
                    log.info("表 {} 不存在，正在执行 SQL 文件 {} 创建表并初始化数据...", tableName, scriptPath);
                    // 执行 SQL 文件
                    executeSqlScript(scriptPath);
                } else {
                    log.info("表 {} 已存在，跳过创建操作。", tableName);
                }
            }
        };
    }

    /**
     * 检查指定表是否存在
     *
     * @param tableName 表名
     * @return 表是否存在
     */
    private boolean checkTableExists(String tableName) {
        try {
            String query = "SELECT COUNT(*) AS count FROM information_schema.tables " +
                    "WHERE LOWER(table_name) = LOWER(?) AND table_schema = 'public'";

            // 使用 queryForRowSet 返回 SqlRowSet
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(query, tableName.toLowerCase());

            if (rowSet.next()) {
                int count = rowSet.getInt("count");
                return count > 0;
            }
            return false;
        } catch (Exception e) {
            log.error("检查表 {} 是否存在时出错: {}", tableName, e.getMessage());
            return false;
        }
    }

    /**
     * 执行 SQL 文件
     *
     * @param scriptPath SQL 文件路径
     */
    private void executeSqlScript(String scriptPath) {
        try {
            // 使用 ClassPathResource 读取 SQL 文件
            Resource resource = new ClassPathResource(scriptPath);
            try (Connection conn = dataSource.getConnection()) {
                ScriptUtils.executeSqlScript(conn, resource);
                log.info("SQL 文件 {} 执行成功！", scriptPath);
            }
        } catch (Exception e) {
            log.error("执行 SQL 文件 {} 时出错: {}", scriptPath, e.getMessage());
        }
    }
}

