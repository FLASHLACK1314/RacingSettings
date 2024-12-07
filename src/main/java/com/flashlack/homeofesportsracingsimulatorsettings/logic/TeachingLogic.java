package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsCarDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsGameDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTeachingDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTrackDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GameTrackCarUuidDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetTeachingDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsCarDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsGameDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsTeachingDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsTrackDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddTeachingVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateTeachingVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.TeachingService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 教学逻辑层
 *
 * @author FLASHLACK
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TeachingLogic implements TeachingService {
    private final SettingsGameDAO settingsGameDAO;
    private final SettingsTrackDAO settingsTrackDAO;
    private final SettingsCarDAO settingsCarDAO;
    private final SettingsTeachingDAO settingsTeachingDAO;

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
    public void adminAddTeaching(String userUuid, AddTeachingVO getData) {
        log.info("管理员添加教学");
        SettingsTeachingDO settingsTeachingDO = settingsTeachingDAO.lambdaQuery()
                .eq(SettingsTeachingDO::getTeachingName, getData.getTeachingName()).one();
        if (settingsTeachingDO != null) {
            throw new BusinessException("教学已存在", ErrorCode.BODY_ERROR);
        }
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(getData.getGameName(), getData.getTrackName(), getData.getCarName());
        SettingsTeachingDO addSettingsTeachingDO = new SettingsTeachingDO();
        addSettingsTeachingDO.setTeachingUuid(UUIDUtils.generateUuid())
                .setGameUuid(gameTrackCarUuidDTO.getGameUuid())
                .setTrackUuid(gameTrackCarUuidDTO.getTrackUuid())
                .setCarUuid(gameTrackCarUuidDTO.getCarUuid())
                .setTeachingName(getData.getTeachingName())
                .setTeachingUrl(getData.getTeachingUrl());
        //保存教学
        log.info("数据库保存教学");
        settingsTeachingDAO.save(addSettingsTeachingDO);
    }

    @Override
    public void checkAddTeachingData(AddTeachingVO getData) {
        log.info("检查添加教学数据");
        if (getData.getGameName() == null || getData.getGameName().isEmpty()) {
            throw new BusinessException("游戏为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getTrackName() == null || getData.getTrackName().isEmpty()) {
            throw new BusinessException("赛道为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getCarName() == null || getData.getCarName().isEmpty()) {
            throw new BusinessException("车辆为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getTeachingName() == null || getData.getTeachingName().isEmpty()) {
            throw new BusinessException("教学名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getTeachingUrl() == null || getData.getTeachingUrl().isEmpty()) {
            throw new BusinessException("教学url为空", ErrorCode.PARAMETER_ERROR);
        }
    }

    @Override
    public void adminDeleteTeaching(String teachingUuid) {
        log.info("管理员删除教学");
        SettingsTeachingDO settingsTeachingDO = settingsTeachingDAO.lambdaQuery()
                .eq(SettingsTeachingDO::getTeachingUuid, teachingUuid).one();
        if (settingsTeachingDO == null) {
            throw new BusinessException("教学不存在", ErrorCode.BODY_ERROR);
        }
        log.info("管理员删除教学「数据库操作」");
        settingsTeachingDAO.removeById(teachingUuid);
    }

    @Override
    public void checkUpdateTeachingData(UpdateTeachingVO getData) {
        log.info("检查修改教学数据");
        if (getData.getTeachingUuid() == null || getData.getTeachingUuid().isEmpty()) {
            throw new BusinessException("教学URL为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getTeachingName() == null || getData.getTeachingName().isEmpty()) {
            throw new BusinessException("教学名称为空", ErrorCode.PARAMETER_ERROR);
        }
        if (getData.getTeachingUrl() == null || getData.getTeachingUrl().isEmpty()) {
            throw new BusinessException("教学url为空", ErrorCode.PARAMETER_ERROR);
        }
    }

    @Override
    public void adminUpdateTeaching(String userUuid, UpdateTeachingVO getData) {
        log.info("管理员修改教学");
        SettingsTeachingDO settingsTeachingDO = settingsTeachingDAO.lambdaQuery()
                .eq(SettingsTeachingDO::getTeachingUuid, getData.getTeachingUuid()).one();
        if (settingsTeachingDO == null) {
            throw new BusinessException("教学不存在", ErrorCode.BODY_ERROR);
        }
        settingsTeachingDO.setTeachingUuid(getData.getTeachingUuid())
                .setTeachingName(getData.getTeachingName())
                .setTeachingUrl(getData.getTeachingUrl());
        log.info("数据库修改教学");
        settingsTeachingDAO.updateById(settingsTeachingDO);
    }

    @Override
    public CustomPage<GetTeachingDTO> getTeaching(String gameName, String trackName,
                                                  String carName, Integer page) {
        // 设置每页大小
        final int pageSize = 10;
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(gameName,
                trackName, carName);
        log.info("获取推荐设置");
        // 1. 计算总数（不加分页限制）
        long total = settingsTeachingDAO.lambdaQuery()
                .eq(SettingsTeachingDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsTeachingDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsTeachingDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .count();
        if (total == 0) {
            throw new BusinessException("数据为空", ErrorCode.OPERATION_INVALID);
        }
        // 2. 查询当前页数据
        int offset = (page - 1) * pageSize;
        List<SettingsTeachingDO> settingsRecords = settingsTeachingDAO.lambdaQuery()
                .eq(SettingsTeachingDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsTeachingDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsTeachingDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .last("LIMIT " + pageSize + " OFFSET " + offset)
                .list();
        if (settingsRecords.isEmpty()) {
            throw new BusinessException("数据为空", ErrorCode.OPERATION_INVALID);
        }
        // 3. 映射查询结果到目标DTO对象
        List<GetTeachingDTO> getTeachingS = settingsRecords.stream()
                .map(settingsTeachingDO -> new GetTeachingDTO()
                        .setTeachingUuid(settingsTeachingDO.getTeachingUuid())
                        .setTeachingName(settingsTeachingDO.getTeachingName())
                        .setTeachingUrl(settingsTeachingDO.getTeachingUrl()))
                .collect(Collectors.toList());

        // 4. 封装为自定义分页对象
        CustomPage<GetTeachingDTO> resultPage = new CustomPage<>();
        resultPage.setRecords(getTeachingS);
        resultPage.setTotal(total);
        resultPage.setSize((long) pageSize);

        return resultPage;

    }
}
