package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.FindPasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.LoginVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.RegisterVO;
import jakarta.validation.Valid;

/**
 * @author FLASHLACK
 */
public interface AuthService  {
    /**
     * 用户登录信息检查
      * @param getData 登录信息
     */
    String checkLoginData(
             @Valid LoginVO getData);

    /**
     * 检查注册数据
     * @param getData 注册数据
     */
    void checkRegisterData(
             RegisterVO getData);

    /**
     * 注册
     * @param getData 注册数据
     */
    void register(
            @Valid RegisterVO getData);

    /**
     * 检查并找回密码
     * @param getData 找回密码数据
     */
    void checkAndFindPasswordData(
            @Valid FindPasswordVO getData);

}
