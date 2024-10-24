package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.EmailCodeDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.dao.UserDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.UserDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeEmailVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeNickNameVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangePasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UserInformationVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.UserService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import com.xlf.utility.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 用户服务实现
 *
 * @author FLASHLACK
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserLogic implements UserService {
    private final UserDAO userDAO;
    private final EmailCodeDAO emailCodeDAO;

    @Override
    public UserInformationVO getUserInformation(String userUuid) {
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUserUuid, userUuid).one();
        UserInformationVO userInformationVO = new UserInformationVO();
        userInformationVO.setUserEmail(userDO.getUserEmail())
                .setNickName(userDO.getNickName());
        return userInformationVO;
    }

    @Override
    public void changePassword(String userUuid, ChangePasswordVO getData) {
        //检查用户是否存在
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUserUuid, userUuid).one();
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        //对于用户邮箱验证
        if (!userDO.getUserEmail().equals(getData.getUserEmail())) {
            throw new BusinessException("邮箱错误", ErrorCode.BODY_ERROR);
        }
        //对于旧密码验证
        if (!PasswordUtil.verify(getData.getOldPassword(), userDO.getUserPassword())) {
            throw new BusinessException("旧密码错误", ErrorCode.BODY_ERROR);
        }
        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, userDO.getUserEmail()).one();
        if (emailCodeDO == null) {
            throw new BusinessException("邮箱验证码不存在", ErrorCode.HEADER_ERROR);
        }
        if (!emailCodeDO.getEmailCode().equals(getData.getEmailCode())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.BODY_ERROR);
        }
        //进行时间校验
        LocalDateTime now = LocalDateTime.now();
        EmailCodeDO emailNowCodeDO = new EmailCodeDO();
        emailNowCodeDO.setCreateTime(now);
        log.info("修改密码-目前时间为：{}", emailNowCodeDO.getCreateTime());
        if (!emailNowCodeDO.getCreateTime().isBefore(emailCodeDO.getExpireTime())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //进行密码确认
        if (!getData.getNewPassword().equals(getData.getConfirmPassword())) {
            throw new BusinessException("新密码不一致", ErrorCode.BODY_ERROR);
        }
        userDO.setUserPassword(PasswordUtil.encrypt(getData.getNewPassword()));
        //进行密码修改
        userDAO.updateUserPasswordByUuid(userDO);
    }

    @Override
    public void changeNickName(String userUuid, ChangeNickNameVO nickName) {
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUserUuid, userUuid).one();
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        userDO.setNickName(nickName.getNickName());
        log.info("数据库修改昵称为：{}", nickName.getNickName());
        userDAO.lambdaUpdate().eq(UserDO::getUserUuid, userUuid)
                .set(UserDO::getNickName, nickName.getNickName()).update();
    }

    @Override
    public void checkEmail(String userUuid, ChangeEmailVO getDate) {
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUserUuid, userUuid).one();
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, userDO.getUserEmail()).one();
        if (emailCodeDO == null) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.HEADER_ERROR);
        }
        EmailCodeDO emailNewCodeDO = emailCodeDAO.lambdaQuery().eq(
                EmailCodeDO::getUserEmail, getDate.getNewEmail()).one();
        if (!emailNewCodeDO.getEmailCode().equals(getDate.getEmailCode())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.BODY_ERROR);
        }
        //进行时间校验
        LocalDateTime now = LocalDateTime.now();
        EmailCodeDO emailNowCodeDO = new EmailCodeDO();
        emailNowCodeDO.setCreateTime(now);
        log.info("修改邮箱-目前时间为：{}", emailNowCodeDO.getCreateTime());
        if (!emailNowCodeDO.getCreateTime().isBefore(emailNewCodeDO.getExpireTime())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
    }

    @Override
    public void changeEmail(String userUuid, ChangeEmailVO getDate) {
        UserDO userDO = userDAO.lambdaQuery().eq(UserDO::getUserUuid, userUuid).one();
        if (userDO == null) {
            throw new BusinessException("用户不存在", ErrorCode.HEADER_ERROR);
        }
        String oldEmail = userDO.getUserEmail();
        userDO.setUserEmail(getDate.getNewEmail());
        userDAO.updateUserEmailByUuid(userDO);
        //为新邮箱添加用户UUID
        emailCodeDAO.lambdaUpdate().eq(EmailCodeDO::getUserEmail, getDate.getNewEmail())
                .set(EmailCodeDO::getUserUuid, userUuid).update();
        // 进行旧邮箱删除操作
        emailCodeDAO.lambdaUpdate().eq(EmailCodeDO::getUserEmail, oldEmail).remove();

    }
}
