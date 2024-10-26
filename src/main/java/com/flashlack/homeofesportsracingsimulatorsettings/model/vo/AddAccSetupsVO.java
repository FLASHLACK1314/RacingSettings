package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.AccSetupsDTO;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 添加ACC设置VO
 * 含多个配置界面，包含轮胎、电子元件、燃料及策略等设置
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
@NotNull
public class AddAccSetupsVO {
     String gameName;
     String trackName;
     String carName;
     String setupsName;
     AccSetupsDTO accSetupsDTO;
}
