package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
    @TableId(type = IdType.NONE)
    String setupsUuid;
    String gameUuid;
    String trackUuid;
    String carUuid;
    String userUuid;
    String setupsName;
    String setups;
    @TableField(value = "recommend")
    Boolean recommend = false;
}
