package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.EmailCodeDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.UserDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.UUIDConstants;
import com.flashlack.homeofesportsracingsimulatorsettings.model.UserDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.FindPasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.LoginVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.RegisterVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.until.UUIDUtils;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 权限逻辑
 *
 * @author FLASHLACK
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthLogic implements AuthService {
    private final EmailCodeDAO emailCodeDAO;
    private final UserDAO userDAO;
    private final RedisService redisService;

    @Override
    public String checkLoginData(LoginVO getData) {
        //检查用户是否存在
        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getUserEmail, getData.getUserEmail()).one();
        if (userDO == null) {
            throw new BusinessException("邮箱或者密码输入错误", ErrorCode.OPERATION_ERROR);
        }
        if (!PasswordUtil.verify(getData.getUserPassword(), userDO.getUserPassword())) {
            throw new BusinessException("邮箱或者密码输入错误", ErrorCode.OPERATION_ERROR);
        } else {
            log.info("登录成功");
            return userDO.getUserUuid();
        }
    }

    @Override
    public void checkRegisterData(RegisterVO getData) {
        //检查确认密码和密码是否一致
        if (!getData.getUserPassword().equals(getData.getConfirmPassword())) {
            throw new BusinessException("密码不一致", ErrorCode.OPERATION_ERROR);
        }
        //验证邮箱验证码，首先在邮箱表内查询出来，然后进行比对
        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, getData.getUserEmail()).one();
        if (emailCodeDO == null) {
            throw new BusinessException("请先获取验证码", ErrorCode.OPERATION_ERROR);
        }
        if (!(emailCodeDO.getUserUuid() == null || emailCodeDO.getUserUuid().isEmpty())) {
            throw new BusinessException("此邮箱已经被注册", ErrorCode.OPERATION_ERROR);
        }
        if (!emailCodeDO.getEmailCode().equals(getData.getEmailCode())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //进行时间校验
        LocalDateTime now = LocalDateTime.now();
        EmailCodeDO emailNowCodeDO = new EmailCodeDO();
        emailNowCodeDO.setCreateTime(now);
        log.info("目前时间为：{}", emailNowCodeDO.getCreateTime());
        if (!emailNowCodeDO.getCreateTime().isBefore(emailCodeDO.getExpireTime())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //检查昵称
        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getNickName, getData.getNickName()).one();
        if (userDO != null) {
            throw new BusinessException("昵称已经被注册", ErrorCode.OPERATION_ERROR);
        }
        //可以进行注册
    }

    @Override
    public void register(RegisterVO getData) {
        //进行数据交换
        UserDO userDO = new UserDO();
        userDO.setUserUuid(UUIDUtils.generateUuid())
                .setRoleUuid(UUIDConstants.ADMIN_ROLE_UUID)
                .setUserEmail(getData.getUserEmail())
                .setUserPassword(PasswordUtil.encrypt(getData.getUserPassword()))
                .setNickName(getData.getNickName());
        //进行注册
        log.info("正在进行注册数据库操作");
        userDAO.save(userDO);
        EmailCodeDO emailCodeDO = new EmailCodeDO();
        //填入邮箱表
        emailCodeDO.setUserEmail(getData.getUserEmail())
                .setUserUuid(userDO.getUserUuid());
        log.info("正在邮箱更新操作");
        log.info("用户UUID为：{}", emailCodeDO.getUserUuid());
        emailCodeDAO.updateUserUuidByEmail(emailCodeDO);
    }

    @Override
    public void checkAndFindPasswordData(FindPasswordVO getData) {
        //检查确认密码和密码是否一致
        if (!getData.getNewPassword().equals(getData.getConfirmPassword())) {
            throw new BusinessException("密码不一致", ErrorCode.OPERATION_ERROR);
        }
        //验证邮箱验证码，首先在邮箱表内查询出来，然后进行比对
        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, getData.getUserEmail()).one();
        if (emailCodeDO == null) {
            throw new BusinessException("此邮箱并未注册", ErrorCode.OPERATION_ERROR);
        }
        if (emailCodeDO.getUserUuid() == null || emailCodeDO.getUserUuid().isEmpty()) {
            throw new BusinessException("此邮箱并未注册", ErrorCode.OPERATION_ERROR);
        }
        log.info("用户数据库内邮箱验证码为:{}", emailCodeDO.getEmailCode());
        if (!emailCodeDO.getEmailCode().equals(getData.getEmailCode())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //进行时间校验
        LocalDateTime now = LocalDateTime.now();
        EmailCodeDO emailNowCodeDO = new EmailCodeDO();
        emailNowCodeDO.setCreateTime(now);
        log.info("校验找回密码的目前时间为：{}", emailNowCodeDO.getCreateTime());
        if (emailNowCodeDO.getCreateTime().isBefore(emailCodeDO.getExpireTime())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //可以进行修改密码
        UserDO userDO = userDAO.lambdaQuery()
                .eq(UserDO::getUserUuid, emailCodeDO.getUserUuid()).one();
        if (userDO == null) {
            throw new BusinessException("系统错误", ErrorCode.NOT_EXIST);
        }
        if (Objects.equals(userDO.getUserPassword(), getData.getNewPassword())) {
            throw new BusinessException("静止设置新密码和旧密码相同", ErrorCode.OPERATION_ERROR);
        }
        userDO.setUserPassword(getData.getNewPassword());
        userDAO.updateUserPasswordByUuid(userDO);
        //删除缓存中的Token
        log.info("正在删除缓存中的Token");
        redisService.deleteTokenFromRedis(userDO.getUserUuid());
    }

}
