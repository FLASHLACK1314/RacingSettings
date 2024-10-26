package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddACCSetupsVO;

/**
 * 设置服务接口
 * @author FLASHLACK
 */
public interface SettingsService {
    /**
     * 添加赛车设置
     * @param userUuid 用户UUID
     * @param getData 添加赛车设置数据
     */
    void addAccSetups(
            String userUuid,
            AddACCSetupsVO getData);
}
