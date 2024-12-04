package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 教学设置实体类
 * @author FLASHLACK1314
 *
 */
@Data
@Accessors(chain = true)
@TableName("settings_teaching")
public class SettingsTeachingDO {
    String teachingUuid;
    String gameUuid;
    String trackUuid;
    String carUuid;
    String teachingName;
    String teachingUrl;
}
