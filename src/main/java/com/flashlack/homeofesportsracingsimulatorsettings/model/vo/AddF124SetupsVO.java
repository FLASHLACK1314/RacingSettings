package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.F124SetupsDTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * F1 2021赛车设置VO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class AddF124SetupsVO {
    String gameName;
    String trackName;
    String carName;
    String setupsName;
    F124SetupsDTO f124SetupsDTO;
}
