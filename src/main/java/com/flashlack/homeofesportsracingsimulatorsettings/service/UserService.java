package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeNickNameVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangePasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UserInformationVO;

/**
 * 用户服务接口
 *
 * @author FLASHLACK
 */
public interface UserService {
    /**
     * 获取用户信息
     *
     * @param userUuid 用户UUID
     * @return 用户信息
     */
    UserInformationVO getUserInformation(
            String userUuid);

    /**
     * 修改密码
     *
     * @param userUuid 用户UUID
     * @param getData  修改密码数据VO
     */
    void changePassword(
            String userUuid,
            ChangePasswordVO getData);

    /**
     * 修改昵称
     * @param userUuid 用户UUID
     * @param nickName 修改昵称VO
     */
    void changeNickName(
            String userUuid,
            ChangeNickNameVO nickName);
}
