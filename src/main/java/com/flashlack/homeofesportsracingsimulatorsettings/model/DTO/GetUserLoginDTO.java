package com.flashlack.homeofesportsracingsimulatorsettings.model.DTO;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户登录返回信息
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
public class GetUserLoginDTO {
    String roleAlias;
    String token;
}
