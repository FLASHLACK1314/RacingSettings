package com.flashlack.homeofesportsracingsimulatorsettings.logic;


import com.flashlack.homeofesportsracingsimulatorsettings.dao.SettingsSetupsDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.AccSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.F124SetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetF124SetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.RoleDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsSetupsDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.UserDO;
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
    private final SettingsSetupsDAO settingsSetupsDAO;


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
    public void userAddAccSetups(String setupsUuid, String setupsName, String userUuid) {
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
        log.info("用户添加推荐ACC设置名字:{}", setupsName);
        //检查用户的的设置名字是否重复是否重复
        if (settingsSetupsDAO.lambdaQuery().eq(SettingsSetupsDO::getUserUuid, userUuid)
                .eq(SettingsSetupsDO::getGameUuid, settingsSetupsDO.getGameUuid())
                .eq(SettingsSetupsDO::getTrackUuid, settingsSetupsDO.getTrackUuid())
                .eq(SettingsSetupsDO::getCarUuid, settingsSetupsDO.getCarUuid())
                .eq(SettingsSetupsDO::getSetupsName, setupsName).exists()) {
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

    @Override
    public GetAccSetupsDTO getRecommendAccSetups(String setupsUuid) {
        //检查设置是否存在
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("设置不存在", ErrorCode.BODY_ERROR);
        }
        if (!settingsSetupsDO.getRecommend()) {
            throw new BusinessException("推荐设置无法获取", ErrorCode.BODY_ERROR);
        }
        GetAccSetupsDTO getAccSetupsDTO = new GetAccSetupsDTO();
        getAccSetupsDTO.setSetupsUuid(settingsSetupsDO.getSetupsUuid())
                .setSetupsName(settingsSetupsDO.getSetupsName())
                .setAccSetupsDTO(gson.fromJson(settingsSetupsDO.getSetups(), AccSetupsDTO.class));
        return getAccSetupsDTO;
    }

    @Override
    public GetF124SetupsDTO getRecommendF124Setups(String setupsUuid) {
        //检查设置是否存在
        SettingsSetupsDO settingsSetupsDO = settingsSetupsDAO.lambdaQuery()
                .eq(SettingsSetupsDO::getSetupsUuid, setupsUuid).one();
        if (settingsSetupsDO == null) {
            throw new BusinessException("设置不存在", ErrorCode.BODY_ERROR);
        }
        if (!settingsSetupsDO.getRecommend()) {
            throw new BusinessException("推荐设置无法获取", ErrorCode.BODY_ERROR);
        }
        GetF124SetupsDTO getF124SetupsDTO = new GetF124SetupsDTO();
        getF124SetupsDTO.setSetupsUuid(settingsSetupsDO.getSetupsUuid())
                .setSetupsName(settingsSetupsDO.getSetupsName())
                .setF124SetupsDTO(gson.fromJson(settingsSetupsDO.getSetups(), F124SetupsDTO.class));
        return getF124SetupsDTO;
    }
}
