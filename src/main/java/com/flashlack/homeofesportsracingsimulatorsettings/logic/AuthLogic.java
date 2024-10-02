package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.EmailCodeDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.RegisterVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 权限逻辑
 * @author FLASHLACK
 */
@Service
@RequiredArgsConstructor
public class AuthLogic implements AuthService {
    private final EmailCodeDAO emailCodeDAO;

    @Override
    public void checkRegisterData(RegisterVO getData) {
        //验证邮箱以及验证码
        if (getData.getUserEmail() == null || getData.getUserEmail().isEmpty()) {
            throw new RuntimeException("邮箱不能为空");
        }
        if (getData.getEmailCode() == null || getData.getEmailCode().isEmpty()) {
            throw new RuntimeException("验证码不能为空");
        }
        if (getData.getUserPassword() == null || getData.getUserPassword().isEmpty()) {
            throw new RuntimeException("密码不能为空");
        }
        if (getData.getNickName() == null || getData.getNickName().isEmpty()) {
            throw new RuntimeException("昵称不能为空");
        }
        //验证邮箱验证码，首先在邮箱表内查询出来，然后进行比对
        EmailCodeDO emailCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail,getData.getUserEmail()).one();
        if (emailCodeDO == null) {
            throw new BusinessException("邮箱未获取验证请先获取验证码", ErrorCode.OPERATION_ERROR);
        }
        if (!emailCodeDO.getEmailCode().equals(getData.getEmailCode())) {
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //进行时间校验
        EmailCodeDO emailNowCodeDO = new EmailCodeDO();
        if (!emailNowCodeDO.getCreateTime().isBefore(emailCodeDO.getExpireTime())){
            throw new BusinessException("邮箱验证码错误", ErrorCode.OPERATION_ERROR);
        }
        //可以进行注册
    }

    @Override
    public void register(RegisterVO getData) {
        //进行注册

    }
}
