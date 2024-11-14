package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetBaseSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetF124SetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddF124SetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateF124SetupsVO;

/**
 * 设置服务接口
 *
 * @author FLASHLACK
 */
public interface SettingsService {
    /**
     * 添加赛车设置
     *
     * @param userUuid 用户UUID
     * @param getData  添加赛车设置数据
     */
    void addAccSetups(
            String userUuid,
            AddAccSetupsVO getData,
            Boolean isRecommend
    );

    /**
     * 获取ACC基础赛车设置信息
     *
     * @param userUuid  用户UUID
     * @param gameName  游戏名称
     * @param trackName 赛道名称
     * @param carName   车辆名称
     * @param page      页数
     * @return 赛车设置链表
     */
    CustomPage<GetBaseSetupsDTO> getBaseSetups(
            String userUuid,
            String gameName,
            String trackName,
            String carName,
            Integer page,
            Boolean isRecommend
    );

    /**
     * 获取ACC详细赛车设置
     *
     * @param userUuid   用户UUID
     * @param setupsUuid 赛车设置UUID
     * @return ACC赛车设置
     */
    GetAccSetupsDTO getAccSetups(
            String userUuid,
            String setupsUuid);

    /**
     * 删除ACC赛车设置
     *
     * @param userUuid   用户UUID
     * @param setupsUuid 赛车设置UUID
     */
    void deleteAccSetups(
            String userUuid,
            String setupsUuid);

    /**
     * 更新ACC赛车设置
     *
     * @param userUuid 用户UUID
     * @param getData  更新赛车设置数据
     */
    void updateAccSetups(
            String userUuid,
            UpdateAccSetupsVO getData);

    /**
     * 添加F1 2024赛车设置
     *
     * @param userUuid 用户UUID
     * @param getData  添加赛车设置数据
     */
    void addF124Setups(
            String userUuid,
            AddF124SetupsVO getData,
            Boolean isRecommend
    );


    /**
     * 获取F124详细赛车设置
     *
     * @param userUuid   用户UUID
     * @param setupsUuid 赛车设置UUID
     * @return F124赛车设置
     */
    GetF124SetupsDTO getF124Setups(
            String userUuid,
            String setupsUuid);


    /**
     * 更新F124赛车设置
     *
     * @param userUuid 用户UUID
     * @param getData  更新赛车设置数据
     */
    void updateF124Setups(
            String userUuid,
            UpdateF124SetupsVO getData);
}
