package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccSetupsDTO;

/**
 * 推荐设置服务接口
 *
 * @author FLASHLACK
 */
public interface RecommendSetupsService {
    /**
     * 检查用户权限
     *
     * @param userUuid  用户UUID
     * @param roleAlias 角色别名
     */
    void checkRole(
            String userUuid,
            String roleAlias);


    /**
     * 删除设置
     *
     * @param setupsUuid 设置UUID
     */
    void deleteSetups(
            String setupsUuid);


    /**
     * 用户添加ACC赛车设置
     * @param setupsUuid 设置UUID
     * @param userName 用户名
     * @param userUuid 用户UUID
     */
    void userAddAccSetups(
            String setupsUuid,
            String userName,
            String userUuid);

    /**
     * 获取推荐ACC赛车设置
     * @param setupsUuid 设置UUID
     * @return ACC赛车设置
     */
    GetAccSetupsDTO getRecommendAccSetups(
            String setupsUuid);
}
