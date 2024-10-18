package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户修改邮箱缓存
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class ChangeEmailVO {
    String newEmail;
    String emailCode;
}
