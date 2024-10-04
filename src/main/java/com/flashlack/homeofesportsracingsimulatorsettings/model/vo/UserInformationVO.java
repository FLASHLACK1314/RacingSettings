package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户信息缓存
 * @author FLASHLACK
 */
@Data
@SuppressWarnings("unused")
@Accessors(chain = true)
public class UserInformationVO {
    String userEmail;
    String nickName;
}
