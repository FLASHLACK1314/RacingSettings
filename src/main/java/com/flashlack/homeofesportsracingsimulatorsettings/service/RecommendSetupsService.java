package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;

/**
 * 推荐设置服务接口
 * @author FLASHLACK
 */
public interface RecommendSetupsService {
    /**
     * 检查用户权限
     * @param userUuid 用户UUID
     * @param roleAlias 角色别名
     */
    void checkRole(
            String userUuid,
            String roleAlias);

    /**
     * 管理员添加ACC赛车设置
     * @param getData 添加ACC赛车设置VO
     */
    void adminAddAccSetups(
            AddAccSetupsVO getData,
            String userUuid);

    /**
     * 删除设置
     * @param setupsUuid 设置UUID
     */
    void deleteSetups(
            String setupsUuid);
}
