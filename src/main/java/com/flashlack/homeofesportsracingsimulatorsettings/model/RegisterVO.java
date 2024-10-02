package com.flashlack.homeofesportsracingsimulatorsettings.model;

import lombok.Data;

/**
 * 注册信息缓存
 *
 * @author 26473
 */
@Data
@SuppressWarnings("unused")
public class RegisterVO {
    String userEmail;
    String userAccount;
    String userPassword;
    String verificationCode;
}
