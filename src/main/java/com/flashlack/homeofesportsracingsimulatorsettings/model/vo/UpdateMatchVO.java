package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 更新比赛VO
 *
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class UpdateMatchVO {
    String matchUuid;
    String matchName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    String matchTime;
    String matchDetails;
}
