package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 赛车设置数据对象
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@TableName("settings_car")
public class SettingsCarDO {
    @TableId(type = IdType.NONE)
    String carUuid;
    String carName;
}
