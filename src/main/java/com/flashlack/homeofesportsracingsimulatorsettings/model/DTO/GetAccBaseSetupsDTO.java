package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取ACC基础设置DTO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GetAccBaseSetupsDTO {
    String setupsUuid;
    String setupsName;
}
