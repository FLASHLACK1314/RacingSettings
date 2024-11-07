package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetUserLoginDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.UserDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.FindPasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.LoginVO;
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
    /**
     * 通过UUID获取用户
     * @param userUuid 用户UUID
     */
    UserDO getUserByUuid(
            String userUuid);

    /**
     * 创建登录返回信息
     * @param userUuid 用户UUID
     * @param token token
     * @return 登录返回信息
     */
    GetUserLoginDTO creatLoginBackInformation(
            String userUuid,
            String token
    );
}
