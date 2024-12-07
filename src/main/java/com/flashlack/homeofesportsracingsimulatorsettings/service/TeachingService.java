package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetTeachingDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddTeachingVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateTeachingVO;

/**
 * 教学服务接口
 *
 * @author FLASHLACK
 */
public interface TeachingService {
    /**
     * 管理员添加教学
     *
     * @param userUuid 用户uuid
     * @param getData  添加教学数据
     */
    void adminAddTeaching(
            String userUuid,
            AddTeachingVO getData);

    /**
     * 检查添加教学数据
     *
     * @param getData 添加教学数据
     */
    void checkAddTeachingData(
            AddTeachingVO getData);

    /**
     * 管理员删除教学
     *
     * @param teachingUuid 教学uuid
     */
    void adminDeleteTeaching(
            String teachingUuid);

    /**
     * 检查修改教学数据
     *
     * @param getData 修改教学数据
     */
    void checkUpdateTeachingData(
            UpdateTeachingVO getData);

    /**
     * 管理员修改教学
     *
     * @param userUuid 用户uuid
     * @param getData  修改教学数据
     */
    void adminUpdateTeaching(
            String userUuid,
            UpdateTeachingVO getData);

    /**
     * 获取教学
     *
     * @param gameName  游戏名称
     * @param trackName 赛道名称
     * @param carName   车辆名称
     */
    CustomPage<GetTeachingDTO> getTeaching(
            String gameName,
            String trackName,
            String carName,
            Integer page
    );
}
