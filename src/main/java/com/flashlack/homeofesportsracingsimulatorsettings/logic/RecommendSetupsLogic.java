package com.flashlack.homeofesportsracingsimulatorsettings.logic;


import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsCarDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsGameDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsSetupsDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsTrackDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GameTrackCarUuidDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.*;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddF124SetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RecommendSetupsService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.UserService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
import com.google.gson.Gson;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 推荐设置逻辑
 *
 * @author FLASHLACK
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendSetupsLogic implements RecommendSetupsService {
    private final UserService userService;
    private final AuthService authService;
    private final Gson gson;
    private final SettingsGameDAO settingsGameDAO;
    private final SettingsTrackDAO settingsTrackDAO;
    private final SettingsCarDAO settingsCarDAO;
    private final SettingsSetupsDAO settingsSetupsDAO;

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
    public void checkRole(String userUuid, String roleAlias) {
        log.info("检查用户权限");
        if (userUuid == null || userUuid.isEmpty()) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        if (roleAlias == null || roleAlias.isEmpty()) {
            throw new BusinessException("角色别名为空", ErrorCode.PARAMETER_ERROR);
        }
        if (!"管理员".equals(roleAlias)) {
            throw new BusinessException("权限不足", ErrorCode.PARAMETER_ERROR);
        }
        //进行数据库查询
        UserDO userDO = userService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        RoleDO role = userService.getRoleByUuid(userDO.getRoleUuid());
        if (role == null) {
            throw new BusinessException("角色不存在", ErrorCode.HEADER_ERROR);
        }
        if (!"管理员".equals(role.getRoleAlias())) {
            throw new BusinessException("权限不足", ErrorCode.BODY_ERROR);
        }
    }

    @Override
    public void adminAddAccSetups(AddAccSetupsVO getData, String userUuid) {
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
                .setSetups(gson.toJson(getData.getAccSetupsDTO()))
                .setRecommend(true);
        //插入数据
        log.info("数据库添加ACC设置-{}", settingsSetupsDO);
        settingsSetupsDAO.save(settingsSetupsDO);
    }

    @Override
    public void deleteSetups(String setupsUuid) {
        //检查设置是否存在
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("设置不存在", ErrorCode.BODY_ERROR);
        }
        //删除设置
        settingsSetupsDAO.removeById(setupsUuid);
    }

    @Override
    public void adminAddF124Setups(AddF124SetupsVO getData, String userUuid) {
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
                .setSetups(gson.toJson(getData.getF124SetupsDTO()))
                .setRecommend(true);
        // 插入数据
        log.info("数据库添加F124设置-{}", settingsSetupsDO);
        settingsSetupsDAO.save(settingsSetupsDO);
    }

    @Override
    public void userAddAccSetups(String setupsUuid,String setupsName,String userUuid) {
        //检查用户是否存在
        UserDO userDO = authService.getUserByUuid(userUuid);
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        //检查设置是否存在
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("设置不存在", ErrorCode.SERVER_INTERNAL_ERROR);
        }
        if (!settingsSetupsDO.getRecommend()) {
            throw new BusinessException("推荐设置无法添加", ErrorCode.SERVER_INTERNAL_ERROR);
        }
        log.info("用户添加推荐ACC设置名字:{}",setupsName);
        //检查用户的的设置名字是否重复是否重复
        if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, settingsSetupsDO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, settingsSetupsDO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, settingsSetupsDO.getCarUuid())
                .eq(SettingsSetupsDO::getSetupsName,setupsName).exists()) {
            throw new BusinessException("设置名字重复", ErrorCode.BODY_ERROR);
        }
        //转换数据
        SettingsSetupsDO userSetupsDO = new SettingsSetupsDO();
        userSetupsDO.setSetupsUuid(UUIDUtils.generateUuid())
                .setGameUuid(settingsSetupsDO.getGameUuid())
                .setTrackUuid(settingsSetupsDO.getTrackUuid())
                .setCarUuid(settingsSetupsDO.getCarUuid())
                .setUserUuid(userUuid)
                .setSetupsName(setupsName)
                .setSetups(settingsSetupsDO.getSetups())
                .setRecommend(false);
        //插入数据
        log.info("用户数据库添加推荐ACC设置-{}", userSetupsDO);
        settingsSetupsDAO.save(userSetupsDO);
    }
}
