package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsCarDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsGameDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsMatchDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTrackDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GameTrackCarUuidDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetMatchListDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsCarDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsGameDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsMatchDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsTrackDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddMatchVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateMatchVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.MatchService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 比赛逻辑
 *
 * @author FLASHLACK
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MatchLogic implements MatchService {
    private final SettingsMatchDAO settingsMatchDAO;
    private final SettingsGameDAO settingsGameDAO;
    private final SettingsTrackDAO settingsTrackDAO;
    private final SettingsCarDAO settingsCarDAO;

    /**
     * 通过游戏名、赛道名、车辆名获取游戏、赛道、车辆的uuid
     *
     * @param gameName  游戏名
     * @param trackName 赛道名
     * @param carName   车辆名
     * @return 游戏、赛道、车辆的uuid
     */
    private GameTrackCarUuidDTO getGameTrackCarUuidByName(String gameName, String trackName, String carName) {
        GameTrackCarUuidDTO gameTrackCarUuidDTO = new GameTrackCarUuidDTO();
        //获取游戏uuid
        log.info("游戏名-{}", gameName);
        SettingsGameDO settingsGameDO = settingsGameDAO.lambdaQuery()
                .eq(SettingsGameDO::getGameName, gameName).one();
        if (settingsGameDO != null) {
            gameTrackCarUuidDTO.setGameUuid(settingsGameDO.getGameUuid());
        }
        log.info("游戏UUID为-{}", gameTrackCarUuidDTO.getGameUuid());
        //获取赛道uuid
        log.info("赛道名-{}", trackName);
        SettingsTrackDO settingsTrackDO = settingsTrackDAO.lambdaQuery()
                .eq(SettingsTrackDO::getTrackName, trackName).one();
        if (settingsTrackDO != null) {
            gameTrackCarUuidDTO.setTrackUuid(settingsTrackDO.getTrackUuid());
        }
        log.info("赛道UUID为-{}", gameTrackCarUuidDTO.getTrackUuid());
        //获取车辆uuid
        log.info("车辆名字为-{}", carName);
        SettingsCarDO settingsCarDO = settingsCarDAO.lambdaQuery().eq(SettingsCarDO::getCarName, carName).one();
        if (settingsCarDO != null) {
            gameTrackCarUuidDTO.setCarUuid(settingsCarDO.getCarUuid());
        }
        log.info("车辆UUID为-{}", gameTrackCarUuidDTO.getCarUuid());
        if (gameTrackCarUuidDTO.getGameUuid() == null || gameTrackCarUuidDTO.getTrackUuid() == null
                || gameTrackCarUuidDTO.getCarUuid() == null) {
            throw new BusinessException("游戏、赛道、车辆不存在", ErrorCode.PARAMETER_ERROR);
        }
        return gameTrackCarUuidDTO;

    }

    @Override
    public void checkAddMatchData(AddMatchVO getData) {
        log.info("检查比赛数据");
        if (getData == null) {
            throw new BusinessException("比赛数据为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getGameName() == null || getData.getGameName().isEmpty()) {
            throw new BusinessException("游戏名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getTrackName() == null || getData.getTrackName().isEmpty()) {
            throw new BusinessException("赛道名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getCarName() == null || getData.getCarName().isEmpty()) {
            throw new BusinessException("车辆名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchName() == null || getData.getMatchName().isEmpty()) {
            throw new BusinessException("比赛名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchTime() == null) {
            throw new BusinessException("比赛时间为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchDetails() == null || getData.getMatchDetails().isEmpty()) {
            throw new BusinessException("比赛详情为空", ErrorCode.PARAMETER_ERROR);
        }
    }

    @Override
    public void adminAddMatch(AddMatchVO getData) throws ParseException {
        log.info("管理员添加比赛");
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(
                getData.getGameName(), getData.getTrackName(), getData.getCarName());
        SettingsMatchDO settingsMatchDO = new SettingsMatchDO();
        log.info("比赛时间为:{}", getData.getMatchTime());
        settingsMatchDO.setMatchUuid(UUIDUtils.generateUuid())
                .setGameUuid(gameTrackCarUuidDTO.getGameUuid())
                .setTrackUuid(gameTrackCarUuidDTO.getTrackUuid())
                .setCarUuid(gameTrackCarUuidDTO.getCarUuid())
                .setMatchName(getData.getMatchName())
                .setMatchTime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .parse(getData.getMatchTime()).getTime()))
                .setMatchDetails(getData.getMatchDetails());
        log.info("DO比赛时间为：{}", settingsMatchDO.getMatchTime());
        log.info("数据库存储管理员添加比赛");
        settingsMatchDAO.save(settingsMatchDO);
    }

    @Override
    public CustomPage<GetMatchListDTO> getMatchList(Integer page, String gameName,
                                                    String startTime, String endTime) throws ParseException {
        // 设置每页大小
        final int pageSize = 10;

        SettingsGameDO settingsGameDO = settingsGameDAO.lambdaQuery()
                .eq(SettingsGameDO::getGameName, gameName).one();
        if (settingsGameDO == null) {
            throw new BusinessException("游戏不存在", ErrorCode.PARAMETER_ERROR);
        }
        log.info("获取推荐设置");
        log.info("结束时间:{}", endTime);
        // 1. 计算总数（不加分页限制）
        long total = settingsMatchDAO.lambdaQuery()
                .eq(SettingsMatchDO::getGameUuid, settingsGameDO.getGameUuid())
                .between(SettingsMatchDO::getMatchTime, new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .parse(startTime).getTime()), new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .parse(endTime).getTime()))
                .count();
        log.info("总数为：{}", total);
        if (total == 0) {
            throw new BusinessException("数据为空", ErrorCode.OPERATION_INVALID);
        }
        // 2. 查询当前页数据
        int offset = (page - 1) * pageSize;
        List<SettingsMatchDO> settingsRecords = settingsMatchDAO.lambdaQuery()
                .eq(SettingsMatchDO::getGameUuid, settingsGameDO.getGameUuid())
                .between(SettingsMatchDO::getMatchTime, new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .parse(startTime).getTime()), new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .parse(endTime).getTime()))
                .last("LIMIT " + pageSize + " OFFSET " + offset)
                .list();
        if (settingsRecords.isEmpty()) {
            throw new BusinessException("数据为空", ErrorCode.OPERATION_INVALID);
        }
        // 3. 映射查询结果到目标DTO对象
        List<GetMatchListDTO> getMatchDtoList = settingsRecords.stream()
                .map(settingsMatchDO -> new GetMatchListDTO()
                        .setMatchUuid(settingsMatchDO.getMatchUuid())
                        .setGameName(settingsGameDO.getGameName())
                        .setTrackName(settingsTrackDAO.lambdaQuery()
                                .eq(SettingsTrackDO::getTrackUuid, settingsMatchDO.getTrackUuid())
                                .one().getTrackName())
                        .setCarName(settingsCarDAO.lambdaQuery()
                                .eq(SettingsCarDO::getCarUuid, settingsMatchDO.getCarUuid())
                                .one().getCarName())
                        .setMatchName(settingsMatchDO.getMatchName())
                        .setMatchTime(String.valueOf(settingsMatchDO.getMatchTime()))
                        .setMatchDetails(settingsMatchDO.getMatchDetails())
                        .setOrganizerUuid(settingsMatchDO.getOrganizerUuid())
                        .setRegistrationStartTime(settingsMatchDO.getRegistrationStartTime() == null
                                ? null
                                : settingsMatchDO.getRegistrationStartTime().toString())
                        .setRegistrationEndTime(settingsMatchDO.getRegistrationEndTime() == null
                                ? null
                                : settingsMatchDO.getRegistrationEndTime().toString())
                        .setMatchStatus(settingsMatchDO.getMatchStatus())
                        .setReviewStatus(settingsMatchDO.getReviewStatus()))
                .collect(Collectors.toList());

        // 4. 封装为自定义分页对象
        CustomPage<GetMatchListDTO> resultPage = new CustomPage<>();
        resultPage.setRecords(getMatchDtoList);
        resultPage.setTotal(total);
        resultPage.setSize((long) pageSize);

        return resultPage;
    }

    @Override
    public void adminDeleteMatch(String matchUuid) {
        SettingsMatchDO settingsMatchDO = settingsMatchDAO.lambdaQuery()
                .eq(SettingsMatchDO::getMatchUuid, matchUuid).one();
        if (settingsMatchDO == null) {
            throw new BusinessException("比赛不存在", ErrorCode.PARAMETER_ERROR);
        }
        log.info("数据库操作删除比赛");
        settingsMatchDAO.removeById(matchUuid);
    }

    @Override
    public void adminUpdateMatch(UpdateMatchVO getData) throws ParseException {
        log.info("管理员更新比赛");
        SettingsMatchDO settingsMatchDO = settingsMatchDAO.lambdaQuery()
                .eq(SettingsMatchDO::getMatchUuid, getData.getMatchUuid()).one();
        if (settingsMatchDO == null) {
            throw new BusinessException("比赛不存在", ErrorCode.PARAMETER_ERROR);
        }
        settingsMatchDO.setMatchTime(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm")
                        .parse(getData.getMatchTime()).getTime()))
                .setMatchName(getData.getMatchName())
                .setMatchDetails(getData.getMatchDetails());
        log.info("数据库操作更新比赛");
        settingsMatchDAO.updateById(settingsMatchDO);
    }

    @Override
    public void checkUpdateMatchVO(UpdateMatchVO getData) {
        if (getData == null) {
            throw new BusinessException("更新比赛数据为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchUuid() == null || getData.getMatchUuid().isEmpty()) {
            throw new BusinessException("比赛UUID为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchName() == null || getData.getMatchName().isEmpty()) {
            throw new BusinessException("比赛名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchTime() == null) {
            throw new BusinessException("比赛时间为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchDetails() == null || getData.getMatchDetails().isEmpty()) {
            throw new BusinessException("比赛详情为空", ErrorCode.PARAMETER_ERROR);
        }
    }

}
