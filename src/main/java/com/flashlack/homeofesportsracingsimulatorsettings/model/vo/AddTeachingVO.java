package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 添加教学VO
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class AddTeachingVO {
    String gameName;
    String trackName;
    String carName;
    String teachingName;
    String teachingUrl;
}
