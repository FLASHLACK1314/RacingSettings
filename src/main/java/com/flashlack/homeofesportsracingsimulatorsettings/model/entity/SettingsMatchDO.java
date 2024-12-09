package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * 比赛设置数据对象
 *
 * @author FLASHLACK
 */
@TableName("settings_match")
@Data
@Accessors(chain = true)
public class SettingsMatchDO {
    @TableId
    String matchUuid;
    String gameUuid;
    String trackUuid;
    String carUuid;
    String matchName;
    Timestamp matchTime;
    String matchDetails;
}
