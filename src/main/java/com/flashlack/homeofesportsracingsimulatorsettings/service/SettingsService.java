package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccBaseSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;

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
            AddAccSetupsVO getData);

    /**
     * 获取赛车设置
     *
     * @param userUuid 用户UUID
     * @param gameName 游戏名称
     * @param trackName 赛道名称
     * @param carName 车辆名称
     * @param page 页数
     * @return 赛车设置链表
     */
    Page<GetAccBaseSetupsDTO> getAccSetups(
            String userUuid,
            String gameName,
            String trackName,
            String carName,
            Integer page
    );

}
