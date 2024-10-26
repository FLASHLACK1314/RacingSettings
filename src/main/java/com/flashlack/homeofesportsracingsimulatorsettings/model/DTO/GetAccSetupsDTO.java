package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取ACC赛车设置DTO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class GetAccSetupsDTO {
    String setupsUuid;
    String setupsName;
    AccSetupsDTO accSetupsDTO;
}
