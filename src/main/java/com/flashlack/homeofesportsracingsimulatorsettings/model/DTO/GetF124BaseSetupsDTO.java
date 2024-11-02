package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * F1 2021基础赛车设置DTO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GetF124BaseSetupsDTO {
    String setupsUuid;
    String setupsName;
}
