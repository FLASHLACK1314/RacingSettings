package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.EmailCodeDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.RoleDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.UserDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetUserLoginDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.EmailCodeDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.RoleDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.UserDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.FindPasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.LoginVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.RegisterVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

/**
 * 权限逻辑
 *
 * @author FLASHLACK
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLogic implements AuthService {

    private static final Set<String> SUPPORTED_REGISTER_ROLES = Set.of("参赛者", "主办方");
    private static final Map<String, String> REGISTER_ROLE_ALIAS_MAP = Map.of(
            "参赛者", "参赛者",
            "主办方", "主办方"
    );

    private final EmailCodeDAO emailCodeDAO;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final RedisService redisService;

    @Override
    public String checkLoginData(LoginVO getData) {
        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getUserEmail, getData.getUserEmail())
                .one();
        if (userDO == null) {
            throw new BusinessException("邮箱或者密码输入错误", ErrorCode.OPERATION_ERROR);
        }
        if (!PasswordUtil.verify(getData.getUserPassword(), userDO.getUserPassword())) {
            throw new BusinessException("邮箱或者密码输入错误", ErrorCode.OPERATION_ERROR);
        }
        log.info("登录成功: {}", userDO.getUserUuid());
        return userDO.getUserUuid();
    }

    @Override
    public void checkRegisterData(RegisterVO getData) {
        if (!getData.getUserPassword().equals(getData.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致", ErrorCode.OPERATION_ERROR);
        }

        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, getData.getUserEmail())
                .one();
        if (emailCodeDO == null) {
            throw new BusinessException("请先获取邮箱验证码", ErrorCode.OPERATION_ERROR);
        }
        if (emailCodeDO.getUserUuid() != null && !emailCodeDO.getUserUuid().isEmpty()) {
            throw new BusinessException("该邮箱已注册", ErrorCode.OPERATION_ERROR);
        }
        if (!emailCodeDO.getEmailCode().equals(getData.getEmailCode())) {
            throw new BusinessException("邮箱验证码不正确", ErrorCode.OPERATION_ERROR);
        }

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(emailCodeDO.getExpireTime())) {
            throw new BusinessException("邮箱验证码已过期", ErrorCode.OPERATION_ERROR);
        }

        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getNickName, getData.getNickName())
                .one();
        if (userDO != null) {
            throw new BusinessException("昵称已被使用", ErrorCode.OPERATION_ERROR);
        }

        if (!SUPPORTED_REGISTER_ROLES.contains(getData.getRoleType())) {
            throw new BusinessException("角色类型不正确", ErrorCode.OPERATION_ERROR);
        }
    }

    @Override
    public void register(RegisterVO getData) {
        String roleAlias = REGISTER_ROLE_ALIAS_MAP.getOrDefault(getData.getRoleType(), "参赛者");

        // 从数据库查询角色UUID
        RoleDO roleDO = roleDAO.lambdaQuery()
                .eq(RoleDO::getRoleAlias, roleAlias)
                .one();
        if (roleDO == null) {
            throw new BusinessException("角色配置异常", ErrorCode.OPERATION_ERROR);
        }

        UserDO userDO = new UserDO();
        userDO.setUserUuid(UUIDUtils.generateUuid())
                .setRoleUuid(roleDO.getRoleUuid())
                .setUserEmail(getData.getUserEmail())
                .setUserPassword(PasswordUtil.encrypt(getData.getUserPassword()))
                .setNickName(getData.getNickName());

        log.info("正在进行注册数据库操作: {}", userDO.getUserUuid());
        userDAO.save(userDO);

        EmailCodeDO emailCodeDO = new EmailCodeDO();
        emailCodeDO.setUserEmail(getData.getUserEmail())
                .setUserUuid(userDO.getUserUuid());
        emailCodeDAO.updateUserUuidByEmail(emailCodeDO);
    }

    @Override
    public void checkAndFindPasswordData(FindPasswordVO getData) {
        if (!getData.getNewPassword().equals(getData.getConfirmPassword())) {
            throw new BusinessException("两次输入的密码不一致", ErrorCode.OPERATION_ERROR);
        }

        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, getData.getUserEmail())
                .one();
        if (emailCodeDO == null) {
            throw new BusinessException("该邮箱并未注册", ErrorCode.OPERATION_ERROR);
        }
        if (emailCodeDO.getUserUuid() == null || emailCodeDO.getUserUuid().isEmpty()) {
            throw new BusinessException("该邮箱并未注册", ErrorCode.OPERATION_ERROR);
        }
        if (!emailCodeDO.getEmailCode().equals(getData.getEmailCode())) {
            throw new BusinessException("邮箱验证码不正确", ErrorCode.OPERATION_ERROR);
        }

        LocalDateTime now = LocalDateTime.now();
        if (!now.isBefore(emailCodeDO.getExpireTime())) {
            throw new BusinessException("邮箱验证码已过期", ErrorCode.OPERATION_ERROR);
        }

        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getUserUuid, emailCodeDO.getUserUuid())
                .one();
        if (userDO == null) {
            throw new BusinessException("系统错误", ErrorCode.NOT_EXIST);
        }
        if (PasswordUtil.verify(getData.getNewPassword(), userDO.getUserPassword())) {
            throw new BusinessException("新密码不能与旧密码相同", ErrorCode.OPERATION_ERROR);
        }

        userDO.setUserPassword(PasswordUtil.encrypt(getData.getNewPassword()));
        userDAO.updateUserPasswordByUuid(userDO);

        redisService.deleteTokenFromRedis(userDO.getUserUuid());
    }

    @Override
    public UserDO getUserByUuid(String userUuid) {
        return userDAO.lambdaQuery()
                .eq(UserDO::getUserUuid, userUuid)
                .one();
    }

    @Override
    public GetUserLoginDTO creatLoginBackInformation(String userUuid, String token) {
        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getUserUuid, userUuid)
                .one();
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.NOT_EXIST);
        }

        RoleDO roleDO = roleDAO.lambdaQuery()
                .eq(RoleDO::getRoleUuid, userDO.getRoleUuid())
                .one();
        if (roleDO == null) {
            throw new BusinessException("系统错误", ErrorCode.NOT_EXIST);
        }

        GetUserLoginDTO getUserLoginDTO = new GetUserLoginDTO();
        getUserLoginDTO.setRoleAlias(roleDO.getRoleAlias())
                .setToken(token);
        return getUserLoginDTO;
    }
}
