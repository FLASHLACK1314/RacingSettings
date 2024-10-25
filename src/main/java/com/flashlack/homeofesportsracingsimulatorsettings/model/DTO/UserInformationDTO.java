package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息传输对象
 * @author  FLASHLACK
 */
@Data
@SuppressWarnings("unused")
@Accessors(chain = true)
public class UserInformationDTO {
    String userEmail;
    String nickName;
}
