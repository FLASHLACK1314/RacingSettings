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
        insertRole(UUIDInitializationConstants.USER_ROLE_UUID, "普通用户", "VIEW, EDIT");
        insertRole(UUIDInitializationConstants.ORGANIZER_ROLE_UUID, "比赛主办方", "MANAGE_MATCH");

        // 插入游戏
        insertGame(UUIDInitializationConstants.GAME_ACC_UUID, "神力科莎:争锋");
        insertGame(UUIDInitializationConstants.GAME_F124_UUID, "F124");

        // 插入车辆
        insertCar(UUIDInitializationConstants.CAR_ACC_FERRARI296_UUID, "法拉利296");
        insertCar(UUIDInitializationConstants.CAR_ACC_HGT3E2_UUID, "兰博基尼GT3EVO2");
        insertCar(UUIDInitializationConstants.CAR_ACC_PORSCHE992R_UUID, "保时捷992R");
        insertCar(UUIDInitializationConstants.CAR_ACC_GTSTANDARDCAR_UUID, "GT统规车");
        insertCar(UUIDInitializationConstants.CAR_F124_F1_UUID, "F1");

        // 插入赛道
        insertTrack(UUIDInitializationConstants.TRACK_SPA_UUID, "斯帕");
        insertTrack(UUIDInitializationConstants.TRACK_MONZA_UUID, "蒙扎");
        insertTrack(UUIDInitializationConstants.TRACK_PAUL_RICARD_UUID, "保罗里卡德");
        insertTrack(UUIDInitializationConstants.TRACK_BRANDS_HATCH_UUID, "布兰茨哈奇");
        insertTrack(UUIDInitializationConstants.TRACK_MISANO_UUID, "米萨诺");
        insertTrack(UUIDInitializationConstants.TRACK_NURBURGRING_NORDSCHLEIFE_UUID, "纽伯格林北环");
        insertTrack(UUIDInitializationConstants.TRACK_ZANDVOORT_UUID, "赞德沃特");
        insertTrack(UUIDInitializationConstants.TRACK_HUNGARORING_UUID, "匈格罗宁");
        insertTrack(UUIDInitializationConstants.TRACK_SILVERSTONE_UUID, "银石");
        insertTrack(UUIDInitializationConstants.TRACK_VALENCIA_UUID, "瓦伦西亚");
        insertTrack(UUIDInitializationConstants.TRACK_IMOLA_UUID, "伊莫拉");
        insertTrack(UUIDInitializationConstants.TRACK_RED_BULL_RING_UUID, "红牛环");
        insertTrack(UUIDInitializationConstants.TRACK_OLTON_PARK_UUID, "奥顿公园");
        insertTrack(UUIDInitializationConstants.TRACK_SNETTERTON_UUID, "斯内特顿");
        insertTrack(UUIDInitializationConstants.TRACK_DONINGTON_PARK_UUID, "多宁顿公园");
        insertTrack(UUIDInitializationConstants.TRACK_AUSTIN_UUID, "奥斯汀");
        insertTrack(UUIDInitializationConstants.TRACK_SPIDER_WAY_UUID, "斯皮德韦");
        insertTrack(UUIDInitializationConstants.TRACK_WATKINS_GLEN_UUID, "沃特金斯峡谷");
        insertTrack(UUIDInitializationConstants.TRACK_LAGUNA_SECA_UUID, "全景山");
        insertTrack(UUIDInitializationConstants.TRACK_SUZUKA_UUID, "铃鹿");
        insertTrack(UUIDInitializationConstants.TRACK_KARAMI_UUID, "卡拉米");
        // 插入赛道
        insertTrack(UUIDInitializationConstants.TRACK_BAHRAIN_UUID, "巴林");
        insertTrack(UUIDInitializationConstants.TRACK_JEDDAH_UUID, "吉达");
        insertTrack(UUIDInitializationConstants.TRACK_AUSTRALIA_UUID, "澳大利亚");
        insertTrack(UUIDInitializationConstants.TRACK_JAPAN_UUID, "日本");
        insertTrack(UUIDInitializationConstants.TRACK_CHINA_UUID, "中国");
        insertTrack(UUIDInitializationConstants.TRACK_MIAMI_UUID, "迈阿密");
        insertTrack(UUIDInitializationConstants.TRACK_MONACO_UUID, "摩纳哥");
        insertTrack(UUIDInitializationConstants.TRACK_CANADA_UUID, "加拿大");
        insertTrack(UUIDInitializationConstants.TRACK_SPAIN_UUID, "西班牙");
        insertTrack(UUIDInitializationConstants.TRACK_AUSTRIA_UUID, "奥地利");
        insertTrack(UUIDInitializationConstants.TRACK_BRITAIN_UUID, "英国");
        insertTrack(UUIDInitializationConstants.TRACK_HUNGARY_UUID, "匈牙利");
        insertTrack(UUIDInitializationConstants.TRACK_BELGIUM_UUID, "比利时");
        insertTrack(UUIDInitializationConstants.TRACK_NETHERLANDS_UUID, "荷兰");
        insertTrack(UUIDInitializationConstants.TRACK_ITALY_UUID, "意大利");
        insertTrack(UUIDInitializationConstants.TRACK_AZERBAIJAN_UUID, "阿塞拜疆");
        insertTrack(UUIDInitializationConstants.TRACK_SINGAPORE_UUID, "新加坡");
        insertTrack(UUIDInitializationConstants.TRACK_COTA_UUID, "美国COTA");
        insertTrack(UUIDInitializationConstants.TRACK_MEXICO_UUID, "墨西哥");
        insertTrack(UUIDInitializationConstants.TRACK_BRAZIL_UUID, "巴西");
        insertTrack(UUIDInitializationConstants.TRACK_LAS_VEGAS_UUID, "拉斯维加斯");
        insertTrack(UUIDInitializationConstants.TRACK_QATAR_UUID, "卡塔尔");
        insertTrack(UUIDInitializationConstants.TRACK_ABU_DHABI_UUID, "阿布扎比");
        insertTrack(UUIDInitializationConstants.TRACK_PORTUGAL_UUID, "葡萄牙");


        // 插入系统常量
        insertSystemConstants("ADMIN_ROLE_UUID", UUIDInitializationConstants.ADMIN_ROLE_UUID);
        insertSystemConstants("USER_ROLE_UUID", UUIDInitializationConstants.USER_ROLE_UUID);
        insertSystemConstants("ORGANIZER_ROLE_UUID", UUIDInitializationConstants.ORGANIZER_ROLE_UUID);
        insertSystemConstants("GAME_ACC_UUID", UUIDInitializationConstants.GAME_ACC_UUID);
        insertSystemConstants("GAME_F124_UUID", UUIDInitializationConstants.GAME_F124_UUID);
        insertSystemConstants("CAR_ACC_FERRARI296_UUID", UUIDInitializationConstants.CAR_ACC_FERRARI296_UUID);
        insertSystemConstants("CAR_ACC_HGT3E2_UUID", UUIDInitializationConstants.CAR_ACC_HGT3E2_UUID);
        insertSystemConstants("CAR_ACC_PORSCHE992R_UUID", UUIDInitializationConstants.CAR_ACC_PORSCHE992R_UUID);
        insertSystemConstants("CAR_F124_F1_UUID", UUIDInitializationConstants.CAR_F124_F1_UUID);
        insertSystemConstants("TRACK_SPA_UUID", UUIDInitializationConstants.TRACK_SPA_UUID);
        insertSystemConstants("TRACK_MONZA_UUID", UUIDInitializationConstants.TRACK_MONZA_UUID);
        insertSystemConstants("TRACK_PAUL_RICARD_UUID", UUIDInitializationConstants.TRACK_PAUL_RICARD_UUID);
        insertSystemConstants("TRACK_BRANDS_HATCH_UUID", UUIDInitializationConstants.TRACK_BRANDS_HATCH_UUID);
        insertSystemConstants("TRACK_MISANO_UUID", UUIDInitializationConstants.TRACK_MISANO_UUID);
        insertSystemConstants("TRACK_NURBURGRING_NORDSCHLEIFE_UUID", UUIDInitializationConstants.TRACK_NURBURGRING_NORDSCHLEIFE_UUID);
        insertSystemConstants("TRACK_ZANDVOORT_UUID", UUIDInitializationConstants.TRACK_ZANDVOORT_UUID);
        insertSystemConstants("TRACK_HUNGARORING_UUID", UUIDInitializationConstants.TRACK_HUNGARORING_UUID);
        insertSystemConstants("TRACK_SILVERSTONE_UUID", UUIDInitializationConstants.TRACK_SILVERSTONE_UUID);
        insertSystemConstants("TRACK_VALENCIA_UUID", UUIDInitializationConstants.TRACK_VALENCIA_UUID);
        insertSystemConstants("TRACK_IMOLA_UUID", UUIDInitializationConstants.TRACK_IMOLA_UUID);
        insertSystemConstants("TRACK_RED_BULL_RING_UUID", UUIDInitializationConstants.TRACK_RED_BULL_RING_UUID);
        insertSystemConstants("TRACK_OLTON_PARK_UUID", UUIDInitializationConstants.TRACK_OLTON_PARK_UUID);
        insertSystemConstants("TRACK_SNETTERTON_UUID", UUIDInitializationConstants.TRACK_SNETTERTON_UUID);
        insertSystemConstants("TRACK_DONINGTON_PARK_UUID", UUIDInitializationConstants.TRACK_DONINGTON_PARK_UUID);
        insertSystemConstants("TRACK_AUSTIN_UUID", UUIDInitializationConstants.TRACK_AUSTIN_UUID);
        insertSystemConstants("TRACK_SPIDER_WAY_UUID", UUIDInitializationConstants.TRACK_SPIDER_WAY_UUID);
        insertSystemConstants("TRACK_WATKINS_GLEN_UUID", UUIDInitializationConstants.TRACK_WATKINS_GLEN_UUID);
        insertSystemConstants("TRACK_LAGUNA_SECA_UUID", UUIDInitializationConstants.TRACK_LAGUNA_SECA_UUID);
        insertSystemConstants("TRACK_SUZUKA_UUID", UUIDInitializationConstants.TRACK_SUZUKA_UUID);
        insertSystemConstants("TRACK_KARAMI_UUID", UUIDInitializationConstants.TRACK_KARAMI_UUID);
        insertSystemConstants("TRACK_BAHRAIN_UUID", UUIDInitializationConstants.TRACK_BAHRAIN_UUID);
        insertSystemConstants("TRACK_JEDDAH_UUID", UUIDInitializationConstants.TRACK_JEDDAH_UUID);
        insertSystemConstants("TRACK_AUSTRALIA_UUID", UUIDInitializationConstants.TRACK_AUSTRALIA_UUID);
        insertSystemConstants("TRACK_JAPAN_UUID", UUIDInitializationConstants.TRACK_JAPAN_UUID);
        insertSystemConstants("TRACK_CHINA_UUID", UUIDInitializationConstants.TRACK_CHINA_UUID);
        insertSystemConstants("TRACK_MIAMI_UUID", UUIDInitializationConstants.TRACK_MIAMI_UUID);
        insertSystemConstants("TRACK_MONACO_UUID", UUIDInitializationConstants.TRACK_MONACO_UUID);
        insertSystemConstants("TRACK_CANADA_UUID", UUIDInitializationConstants.TRACK_CANADA_UUID);
        insertSystemConstants("TRACK_SPAIN_UUID", UUIDInitializationConstants.TRACK_SPAIN_UUID);
        insertSystemConstants("TRACK_AUSTRIA_UUID", UUIDInitializationConstants.TRACK_AUSTRIA_UUID);
        insertSystemConstants("TRACK_BRITAIN_UUID", UUIDInitializationConstants.TRACK_BRITAIN_UUID);
        insertSystemConstants("TRACK_HUNGARY_UUID", UUIDInitializationConstants.TRACK_HUNGARY_UUID);
        insertSystemConstants("TRACK_BELGIUM_UUID", UUIDInitializationConstants.TRACK_BELGIUM_UUID);
        insertSystemConstants("TRACK_NETHERLANDS_UUID", UUIDInitializationConstants.TRACK_NETHERLANDS_UUID);
        insertSystemConstants("TRACK_ITALY_UUID", UUIDInitializationConstants.TRACK_ITALY_UUID);
        insertSystemConstants("TRACK_AZERBAIJAN_UUID", UUIDInitializationConstants.TRACK_AZERBAIJAN_UUID);
        insertSystemConstants("TRACK_SINGAPORE_UUID", UUIDInitializationConstants.TRACK_SINGAPORE_UUID);
        insertSystemConstants("TRACK_COTA_UUID", UUIDInitializationConstants.TRACK_COTA_UUID);
        insertSystemConstants("TRACK_MEXICO_UUID", UUIDInitializationConstants.TRACK_MEXICO_UUID);
        insertSystemConstants("TRACK_BRAZIL_UUID", UUIDInitializationConstants.TRACK_BRAZIL_UUID);
        insertSystemConstants("TRACK_LAS_VEGAS_UUID", UUIDInitializationConstants.TRACK_LAS_VEGAS_UUID);
        insertSystemConstants("TRACK_QATAR_UUID", UUIDInitializationConstants.TRACK_QATAR_UUID);
        insertSystemConstants("TRACK_ABU_DHABI_UUID", UUIDInitializationConstants.TRACK_ABU_DHABI_UUID);
        insertSystemConstants("TRACK_PORTUGAL_UUID", UUIDInitializationConstants.TRACK_PORTUGAL_UUID);
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
