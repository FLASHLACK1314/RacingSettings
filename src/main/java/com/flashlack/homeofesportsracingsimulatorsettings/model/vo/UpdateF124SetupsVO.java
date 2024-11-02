package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.F124SetupsDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 更新F124设置VO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class UpdateF124SetupsVO {
    String setupsUuid;
    String setupsName;
    F124SetupsDTO f124SetupsDTO;
}
