package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddTeachingVO;

/**
 * 教学服务接口
 * @author FLASHLACK
 */
public interface TeachingService {
    /**
     * 管理员添加教学
     * @param userUuid 用户uuid
     * @param getData 添加教学数据
     */
    void adminAddTeaching(
            String userUuid,
            AddTeachingVO getData);

    /**
     * 检查添加教学数据
     * @param getData 添加教学数据
     */
    void checkAddTeachingData(
            AddTeachingVO getData);
}
