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
@Data
@TableName("settings_track")
@Accessors(chain = true)
public class SettingsTrackDO {
    @TableId(type = IdType.NONE)
    String trackUuid;
    String trackName;
}
