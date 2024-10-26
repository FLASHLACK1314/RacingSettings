package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取ACC赛车设置VO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class GetAccSetupsVO {
    String gameName;
    String trackName;
    String carName;
}
