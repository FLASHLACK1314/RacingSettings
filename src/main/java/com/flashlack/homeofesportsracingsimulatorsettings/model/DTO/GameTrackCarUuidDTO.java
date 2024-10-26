package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 游戏赛道赛车UUID数据交换
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GameTrackCarUuidDTO {
    String gameUuid;
    String trackUuid;
    String carUuid;
}
