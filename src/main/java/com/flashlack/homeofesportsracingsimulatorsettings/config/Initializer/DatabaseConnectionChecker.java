package com.flashlack.homeofesportsracingsimulatorsettings.config.Initializer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接检查器
 * 在应用启动时优先检查数据库连接是否可用
 *
 * @author FLASHLACK
 */
@Slf4j
@Configuration
public class DatabaseConnectionChecker {

    private final DataSource dataSource;

    public DatabaseConnectionChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    @Order(0)
    public CommandLineRunner checkDatabaseConnection() {
        return args -> {
            log.info("开始检查数据库连接...");
            
            try (Connection connection = dataSource.getConnection()) {
                if (connection != null && !connection.isClosed()) {
                    log.info("✓ 数据库连接成功！");
                    log.info("数据库信息: {}", connection.getMetaData().getURL());
                    log.info("数据库类型: {}", connection.getMetaData().getDatabaseProductName());
                    log.info("数据库版本: {}", connection.getMetaData().getDatabaseProductVersion());
                } else {
                    String errorMsg = "数据库连接失败：连接对象为空或已关闭";
                    log.error(errorMsg);
                    throw new RuntimeException(errorMsg);
                }
            } catch (SQLException e) {
                String errorMsg = String.format("数据库连接失败: %s", e.getMessage());
                log.error("=" .repeat(80));
                log.error("数据库连接错误详情:");
                log.error("错误代码: {}", e.getErrorCode());
                log.error("SQL状态: {}", e.getSQLState());
                log.error("错误信息: {}", e.getMessage());
                log.error("=" .repeat(80));
                log.error("请检查以下配置:");
                log.error("1. 数据库服务是否已启动");
                log.error("2. application-dev.yaml 中的数据库连接配置是否正确");
                log.error("3. 数据库用户名和密码是否正确");
                log.error("4. 数据库名称是否存在");
                log.error("5. 数据库端口是否正确 (默认PostgreSQL: 5432)");
                log.error("=" .repeat(80));
                
                // 抛出运行时异常，终止应用启动
                throw new RuntimeException(errorMsg, e);
            } catch (Exception e) {
                String errorMsg = String.format("数据库连接检查时发生未知错误: %s", e.getMessage());
                log.error(errorMsg, e);
                throw new RuntimeException(errorMsg, e);
            }
        };
    }
}
