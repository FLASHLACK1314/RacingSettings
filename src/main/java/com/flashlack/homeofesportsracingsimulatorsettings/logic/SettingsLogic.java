package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsCarDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsGameDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsSetupsDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTrackDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GameTrackCarUuidDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccBaseSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.*;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.SettingsService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
import com.google.gson.Gson;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FLASHLACK
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsLogic implements SettingsService {
    private final AuthService authService;
    private final SettingsSetupsDAO settingsSetupsDAO;
    private final SettingsCarDAO settingsCarDAO;
    private final SettingsGameDAO settingsGameDAO;
    private final SettingsTrackDAO settingsTrackDAO;
    private final Gson gson;

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
        log.info("游戏UUID为-{}",gameTrackCarUuidDTO.getGameUuid());
        //获取赛道uuid
        log.info("赛道名-{}", trackName);
        SettingsTrackDO settingsTrackDO = settingsTrackDAO.lambdaQuery()
                .eq(SettingsTrackDO::getTrackName, trackName).one();
        if (settingsTrackDO != null) {
            gameTrackCarUuidDTO.setTrackUuid(settingsTrackDO.getTrackUuid());
        }
        log.info("赛道UUID为-{}", gameTrackCarUuidDTO.getTrackUuid());
        //获取车辆uuid
        log.info("车辆名字为-{}",carName);
        SettingsCarDO settingsCarDO = settingsCarDAO.lambdaQuery().eq(SettingsCarDO::getCarName, carName).one();
        if (settingsCarDO != null) {
            gameTrackCarUuidDTO.setCarUuid(settingsCarDO.getCarUuid());
        }
        log.info("车辆UUID为-{}",gameTrackCarUuidDTO.getCarUuid());
        if (gameTrackCarUuidDTO.getGameUuid() == null || gameTrackCarUuidDTO.getTrackUuid() == null
                || gameTrackCarUuidDTO.getCarUuid() == null) {
            throw new BusinessException("游戏、赛道、车辆不存在", ErrorCode.PARAMETER_ERROR);
        }
        return gameTrackCarUuidDTO;

    }

    @Override
    public void addAccSetups(String userUuid, AddAccSetupsVO getData) {
        //准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        //检查用户的的设置名字是否重复是否重复
        if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsName, getData.getSetupsName()).one() != null) {
            throw new BusinessException("设置名字重复", ErrorCode.BODY_ERROR);
        }
        //准备游戏、赛道、车辆数据
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(getData.getGameName(),
                getData.getTrackName(), getData.getCarName());
        log.info("游戏、赛道、车辆uuid-{}", gameTrackCarUuidDTO);
        //转换数据
        SettingsSetupsDO settingsSetupsDO = new SettingsSetupsDO();
        settingsSetupsDO.setSetupsUuid(UUIDUtils.generateUuid())
                .setGameUuid(gameTrackCarUuidDTO.getGameUuid())
                .setTrackUuid(gameTrackCarUuidDTO.getTrackUuid())
                .setCarUuid(gameTrackCarUuidDTO.getCarUuid())
                .setUserUuid(userUuid)
                .setSetupsName(getData.getSetupsName())
                .setSetups(gson.toJson(getData.getAccSetupsDTO()));
        //插入数据
        log.info("数据库添加ACC设置-{}", settingsSetupsDO);
        settingsSetupsDAO.save(settingsSetupsDO);
    }



    @Override
    public CustomPage<GetAccBaseSetupsDTO> getAccSetups(String userUuid, String gameName, String trackName,
                                                        String carName, Integer page) {
        // 设置每页大小
        final int pageSize = 10;

        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }

        // 创建分页参数
        Page<SettingsSetupsDO> pageParam = new Page<>(page, pageSize);
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(gameName, trackName, carName);

        // 查询满足条件的数据，分页处理
        Page<SettingsSetupsDO> settingsPage = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .page(pageParam);

        // 将查询结果映射到目标DTO对象
        List<GetAccBaseSetupsDTO> getAccSetupsDTOList = settingsPage.getRecords().stream()
                .map(settingsSetupsDO -> {
                    GetAccBaseSetupsDTO getAccBaseSetupsDTO = new GetAccBaseSetupsDTO();
                    getAccBaseSetupsDTO.setSetupsUuid(settingsSetupsDO.getSetupsUuid())
                            .setSetupsName(settingsSetupsDO.getSetupsName());
                    return getAccBaseSetupsDTO;
                }).collect(Collectors.toList());

        // 封装为自定义分页对象
        CustomPage<GetAccBaseSetupsDTO> resultPage = new CustomPage<>();
        resultPage.setRecords(getAccSetupsDTOList);
        resultPage.setTotal(settingsPage.getTotal());
        resultPage.setSize((long) pageSize);

        return resultPage;
    }




}