package com.flashlack.homeofesportsracingsimulatorsettings.service;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetMatchListDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddMatchVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateMatchVO;

import java.text.ParseException;

/**
 * 比赛设置服务接口
 *
 * @author FLASHLACK
 */
public interface MatchService {
    /**
     * 检查添加比赛数据
     *
     * @param getData 添加比赛数据
     */
    void checkAddMatchData(
            AddMatchVO getData);

    /**
     * 管理员添加比赛
     *
     * @param getData 添加比赛数据
     */
    void adminAddMatch(
            AddMatchVO getData) throws ParseException;

    /**
     * 获取比赛列表
     *
     * @param page      页数
     * @param gameName  游戏名称
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 比赛列表
     */
    CustomPage<GetMatchListDTO> getMatchList(
            Integer page,
            String gameName,
            String startTime,
            String endTime
    ) throws ParseException;

    /**
     * 管理员删除比赛
     *
     * @param matchUuid 比赛UUID
     */
    void adminDeleteMatch(
            String matchUuid);

    /**
     * 管理员更新比赛
     *
     * @param getData 更新比赛数据
     */
    void adminUpdateMatch(
            UpdateMatchVO getData) throws ParseException;

    /**
     * 检查更新比赛数据
     *
     * @param getData 更新比赛数据
     */
    void checkUpdateMatchVO(
            UpdateMatchVO getData);
}
