package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.RegisterVO;

/**
 * @author FLASHLACK
 */
public interface AuthService  {
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
    void register(RegisterVO getData);
}
