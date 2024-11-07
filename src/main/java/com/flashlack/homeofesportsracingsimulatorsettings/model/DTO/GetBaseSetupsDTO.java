package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取ACC基础设置DTO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GetBaseSetupsDTO {
    String setupsUuid;
    String setupsName;
}
