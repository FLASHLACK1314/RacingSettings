package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.AccSetupsDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 更新ACC设置VO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class UpdateAccSetupsVO {
    String setupsUuid;
    String setupsName;
    AccSetupsDTO accSetupsDTO;
}
