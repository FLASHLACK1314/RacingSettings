package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 游戏设置数据对象
 * @author FLASHLACK
 */
@TableName("settings_game")
@Data
@Accessors(chain = true)
public class SettingsGameDO {
    @TableId(type = IdType.NONE)
    String gameUuid;
    String gameName;
}
