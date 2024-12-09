package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsCarDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsGameDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsMatchDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTrackDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GameTrackCarUuidDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsCarDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsGameDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsTrackDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddMatchVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.MatchService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        if (getData.getMatchTime() == null || getData.getMatchTime().isEmpty()) {
            throw new BusinessException("比赛时间为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getMatchDetails() == null || getData.getMatchDetails().isEmpty()) {
            throw new BusinessException("比赛详情为空", ErrorCode.PARAMETER_ERROR);
        }
    }

    @Override
    public void adminAddMatch(AddMatchVO getData) {
        log.info("管理员添加比赛");
    }
}
