package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsCarDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsGameDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsSetupsDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTrackDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.*;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.*;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddF124SetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateF124SetupsVO;
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
    public void addAccSetups(String userUuid, AddAccSetupsVO getData) {
        //准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        //准备游戏、赛道、车辆数据
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(getData.getGameName(),
                getData.getTrackName(), getData.getCarName());
        log.info("游戏、赛道、车辆uuid-{}", gameTrackCarUuidDTO);
        //检查用户的的设置名字是否重复是否重复
        if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .eq(SettingsSetupsDO::getSetupsName, getData.getSetupsName()).one() != null) {
            throw new BusinessException("设置名字重复", ErrorCode.BODY_ERROR);
        }
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
    public CustomPage<GetBaseSetupsDTO> getAccBaseSetups(String userUuid, String gameName, String trackName,
                                                         String carName, Integer page) {
        // 设置每页大小
        final int pageSize = 10;

        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }

        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(gameName, trackName, carName);

        // 1. 计算总数（不加分页限制）
        long total = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .count();
        if (total == 0) {
            throw new BusinessException("数据为空", ErrorCode.OPERATION_INVALID);
        }
        // 2. 查询当前页数据
        int offset = (page - 1) * pageSize;
        List<SettingsSetupsDO> settingsRecords = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .last("LIMIT " + pageSize + " OFFSET " + offset)
                .list();
        if (settingsRecords.isEmpty()) {
            throw new BusinessException("数据为空", ErrorCode.OPERATION_INVALID);
        }
        // 3. 映射查询结果到目标DTO对象
        List<GetBaseSetupsDTO> getAccSetupsDTOList = settingsRecords.stream()
                .map(settingsSetupsDO -> new GetBaseSetupsDTO()
                        .setSetupsUuid(settingsSetupsDO.getSetupsUuid())
                        .setSetupsName(settingsSetupsDO.getSetupsName()))
                .collect(Collectors.toList());

        // 4. 封装为自定义分页对象
        CustomPage<GetBaseSetupsDTO> resultPage = new CustomPage<>();
        resultPage.setRecords(getAccSetupsDTOList);
        resultPage.setTotal(total);
        resultPage.setSize((long) pageSize);

        return resultPage;
    }

    @Override
    public GetAccSetupsDTO getAccSetups(String userUuid, String setupsUuid) {
        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        log.info("获取的赛车设置-{}", settingsSetupsDO);
        if (settingsSetupsDO != null) {
            //进行数据交换
            GetAccSetupsDTO getAccSetupsDTO = new GetAccSetupsDTO();
            getAccSetupsDTO.setSetupsUuid(settingsSetupsDO.getSetupsUuid())
                    .setSetupsName(settingsSetupsDO.getSetupsName())
                    .setAccSetupsDTO(gson.fromJson(settingsSetupsDO.getSetups(), AccSetupsDTO.class));
            return getAccSetupsDTO;
        } else {
            throw new BusinessException("暂无此赛车设置", ErrorCode.PARAMETER_ERROR);
        }
    }

    @Override
    public void deleteAccSetups(String userUuid, String setupsUuid) {
        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("赛车设置不存在", ErrorCode.BODY_ERROR);
        }
        if (settingsSetupsDO.getRecommend()){
            throw new BusinessException("推荐设置不可删除", ErrorCode.BODY_ERROR);
        }
        // 删除数据
        if (!settingsSetupsDAO.lambdaUpdate().eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).remove()) {
            throw new BusinessException("删除失败", ErrorCode.PARAMETER_ERROR);
        }
    }

    @Override
    public void updateAccSetups(String userUuid, UpdateAccSetupsVO getData) {
        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        // 准备游戏、赛道、车辆数据
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsUuid, getData.getSetupsUuid()).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("赛车设置不存在", ErrorCode.BODY_ERROR);
        }
        if (!settingsSetupsDO.getSetupsName().equals(getData.getSetupsName())) {
            if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                    .eq(SettingsSetupsDO::getSetupsName, getData.getSetupsName()).one() != null) {
                throw new BusinessException("设置名字重复", ErrorCode.BODY_ERROR);
            }
        }
        settingsSetupsDO.setSetupsName(getData.getSetupsName())
                .setSetups(gson.toJson(getData.getAccSetupsDTO()));
        if (!settingsSetupsDAO.updateById(settingsSetupsDO)) {
            throw new BusinessException("更新失败", ErrorCode.SERVER_INTERNAL_ERROR);
        }
    }

    @Override
    public void addF124Setups(String userUuid, AddF124SetupsVO getData) {
        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        // 准备游戏、赛道、车辆数据
        GameTrackCarUuidDTO gameTrackCarUuidDTO = getGameTrackCarUuidByName(getData.getGameName(),
                getData.getTrackName(), getData.getCarName());
        log.info("F124 游戏、赛道、车辆uuid-{}", gameTrackCarUuidDTO);
        // 检查用户的的设置名字是否重复是否重复
        if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, gameTrackCarUuidDTO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, gameTrackCarUuidDTO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, gameTrackCarUuidDTO.getCarUuid())
                .eq(SettingsSetupsDO::getSetupsName, getData.getSetupsName()).one() != null) {
            throw new BusinessException("设置名字重复", ErrorCode.BODY_ERROR);
        }
        // 转换数据
        SettingsSetupsDO settingsSetupsDO = new SettingsSetupsDO();
        settingsSetupsDO.setSetupsUuid(UUIDUtils.generateUuid())
                .setGameUuid(gameTrackCarUuidDTO.getGameUuid())
                .setTrackUuid(gameTrackCarUuidDTO.getTrackUuid())
                .setCarUuid(gameTrackCarUuidDTO.getCarUuid())
                .setUserUuid(userUuid)
                .setSetupsName(getData.getSetupsName())
                .setSetups(gson.toJson(getData.getF124SetupsDTO()));
        // 插入数据
        log.info("数据库添加F124设置-{}", settingsSetupsDO);
        settingsSetupsDAO.save(settingsSetupsDO);
    }


    @Override
    public GetF124SetupsDTO getF124Setups(String userUuid, String setupsUuid) {
        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        log.info("获取F124的赛车设置-{}", settingsSetupsDO);
        if (settingsSetupsDO != null) {
            // 进行数据交换
            GetF124SetupsDTO getF124SetupsDTO = new GetF124SetupsDTO();
            getF124SetupsDTO.setSetupsUuid(settingsSetupsDO.getSetupsUuid())
                    .setSetupsName(settingsSetupsDO.getSetupsName())
                    .setF124SetupsDTO(gson.fromJson(settingsSetupsDO.getSetups(), F124SetupsDTO.class));
            return getF124SetupsDTO;
        } else {
            throw new BusinessException("暂无此赛车设置", ErrorCode.PARAMETER_ERROR);
        }
    }


    @Override
    public void updateF124Setups(String userUuid, UpdateF124SetupsVO getData) {
        // 准备用户数据
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        // 准备游戏、赛道、车辆数据
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getSetupsUuid, getData.getSetupsUuid()).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("赛车设置不存在", ErrorCode.BODY_ERROR);
        }
        if (!settingsSetupsDO.getSetupsName().equals(getData.getSetupsName())) {
            if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                    .eq(SettingsSetupsDO::getSetupsName, getData.getSetupsName()).one() != null) {
                throw new BusinessException("设置名字重复", ErrorCode.BODY_ERROR);
            }
        }
        // 转换数据
        settingsSetupsDO.setSetupsName(getData.getSetupsName())
                .setSetups(gson.toJson(getData.getF124SetupsDTO()));
        log.info("更新的F124赛车设置-{}", settingsSetupsDO);
        // 更新数据
        if (!settingsSetupsDAO.updateById(settingsSetupsDO)) {
            throw new BusinessException("更新失败", ErrorCode.SERVER_INTERNAL_ERROR);
        }
    }


}
