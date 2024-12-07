package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 更新教程VO
 * @author FALSHLACK
 */
@Data
@Accessors(chain = true)
public class UpdateTeachingVO {
    String teachingUuid;
    String teachingName;
    String teachingUrl;
}
