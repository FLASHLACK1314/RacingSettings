package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取教学DTO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GetTeachingDTO {
    String teachingUuid;
    String teachingName;
    String teachingUrl;
}
