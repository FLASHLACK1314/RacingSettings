package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddMatchVO;

/**
 * 比赛设置服务接口
 * @author FLASHLACK
 */
public interface MatchService {
    /**
     * 检查添加比赛数据
     * @param getData 添加比赛数据
     */
    void checkAddMatchData(
            AddMatchVO getData);

    /**
     * 管理员添加比赛
     * @param getData 添加比赛数据
     */
    void adminAddMatch(
            AddMatchVO getData);
}
