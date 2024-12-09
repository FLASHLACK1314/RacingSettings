package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 获取比赛列表DTO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GetMatchListDTO {
    String matchUuid;
    String gameName;
    String trackName;
    String carName;
    String matchName;
    String matchTime;
    String matchDetails;
}
