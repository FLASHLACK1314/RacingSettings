package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 设置信息实体类
 *
 * @author FLASHLACK
 */
@Data
@TableName("settings_setups")
@SuppressWarnings("unused")
@Accessors(chain = true)
public class SettingsSetupsDO {
    String setupsUuid;
    String gameUuid;
    String trackUuid;
    String carUuid;
    String setupsName;
    String setups;
}
