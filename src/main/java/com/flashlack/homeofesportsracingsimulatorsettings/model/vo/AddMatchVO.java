package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 添加比赛VO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class AddMatchVO {
    String gameName;
    String trackName;
    String carName;
    String matchName;
    String matchTime;
    String matchDetails;
}
