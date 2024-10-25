package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.EmailCodeMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.EmailCodeDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


/**
 * 邮箱验证码 DAO
 *
 * @author FLASHLACK
 */

@Slf4j
@Repository
public class EmailCodeDAO extends ServiceImpl<EmailCodeMapper, EmailCodeDO>
        implements IService<EmailCodeDO> {

    /**
     * 通过邮箱更新邮箱和 UUID 字段
     *
     * @param emailCodeDO 邮箱验证码实体类
     */
    public void updateUserUuidByEmail(EmailCodeDO emailCodeDO) {
        this.lambdaUpdate().eq(EmailCodeDO::getUserEmail, emailCodeDO.getUserEmail())
                .set(EmailCodeDO::getUserUuid, emailCodeDO.getUserUuid())
                .set(EmailCodeDO::getExpireTime, LocalDateTime.now().plusMinutes(5))
                .set(EmailCodeDO::getCreateTime, LocalDateTime.now())
                .update();
        log.info("更新邮箱和用户UUID数据库中");
    }

    /**
     * 通过邮箱更新邮箱验证码字段
     *
     * @param emailCodeDO 邮箱验证码实体类
     */
    public void updateEmailCodeByEmail(EmailCodeDO emailCodeDO) {
        // 设置更新条件：根据主键 ID 来匹配
        this.lambdaUpdate().eq(EmailCodeDO::getUserEmail, emailCodeDO.getUserEmail())
                .set(EmailCodeDO::getEmailCode, emailCodeDO.getEmailCode())
                .set(EmailCodeDO::getExpireTime, LocalDateTime.now().plusMinutes(5))
                .set(EmailCodeDO::getCreateTime, LocalDateTime.now())
                .update();
        log.info("更新邮箱验证码数据库中");
    }
}
