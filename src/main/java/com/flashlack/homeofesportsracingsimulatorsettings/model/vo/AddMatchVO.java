package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 添加比赛VO
 *
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class AddMatchVO {
    String gameName;
    String trackName;
    String carName;
    String matchName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime matchTime;
    String matchDetails;
}
