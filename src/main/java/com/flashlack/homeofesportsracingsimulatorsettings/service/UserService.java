package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.UserInformationDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.RoleDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.UserDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeEmailVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeNickNameVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangePasswordVO;

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
    UserInformationDTO getUserInformation(
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

    /**
     * 检查邮箱
     * @param userUuid 用户UUID
     * @param getDate 修改邮箱VO
     */
    void checkEmail(
            String userUuid,
            ChangeEmailVO getDate);

    /**
     * 修改邮箱
     * @param userUuid 用户UUID
     * @param getDate 修改邮箱VO
     */
    void changeEmail(
            String userUuid,
            ChangeEmailVO getDate);

    /**
     * 获取用户信息
     * @param userUuid 用户UUID
     * @return 用户信息
     */
    UserDO getUserByUuid(
            String userUuid);
    /**
     * 通过角色UUID获取角色
     * @param roleUuid 角色UUID
     * @return 角色
     */
    RoleDO getRoleByUuid(
            String roleUuid);
}
