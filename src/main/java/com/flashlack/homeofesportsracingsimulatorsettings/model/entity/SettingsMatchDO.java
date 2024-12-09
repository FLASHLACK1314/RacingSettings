package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    LocalDateTime matchTime;
    String matchDetails;

    /**
     * 设置比赛时间，只保留到分钟
     *
     * @param matchTime LocalDateTime 对象
     * @return 当前对象（链式调用支持）
     */
    public SettingsMatchDO setMatchTime(LocalDateTime matchTime) {
        this.matchTime = matchTime != null ?
                matchTime.truncatedTo(ChronoUnit.MINUTES) : null;
        return this;
    }
}
