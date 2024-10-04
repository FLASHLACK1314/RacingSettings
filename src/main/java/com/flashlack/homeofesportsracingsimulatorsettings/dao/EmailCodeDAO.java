package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.EmailCodeMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


/**
 * 邮箱验证码 DAO
 *
 * @author FLASHLACK
 */
@Repository
@Slf4j
public class EmailCodeDAO extends ServiceImpl<EmailCodeMapper, EmailCodeDO>
        implements IService<EmailCodeDO> {
    private final EmailCodeMapper emailCodeMapper;

    public EmailCodeDAO(EmailCodeMapper emailCodeMapper) {
        this.emailCodeMapper = emailCodeMapper;
    }

    /**
     * 通过邮箱更新邮箱和 UUID 字段
     *
     * @param emailCodeDO 邮箱验证码实体类
     */
    public void updateUserUuidByEmail(EmailCodeDO emailCodeDO) {
        // 创建 LambdaUpdateWrapper 来构建更新条件和更新内容
        LambdaUpdateWrapper<EmailCodeDO> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置更新条件：根据主键 ID 来匹配
        updateWrapper.eq(EmailCodeDO::getUserEmail, emailCodeDO.getUserEmail())
                // 设置要更新的字段内容
                .set(EmailCodeDO::getUserUuid, emailCodeDO.getUserUuid());
        log.info("更新邮箱和用户UUID数据库中");
        // 调用 Mapper 的 update 方法执行更新操作
        emailCodeMapper.update(null, updateWrapper);
    }

    /**
     * 通过邮箱更新邮箱验证码字段
     *
     * @param emailCodeDO 邮箱验证码实体类
     */
    public void updateEmailCodeByEmail(EmailCodeDO emailCodeDO) {
        // 创建 LambdaUpdateWrapper 来构建更新条件和更新内容
        LambdaUpdateWrapper<EmailCodeDO> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置更新条件：根据主键 ID 来匹配
        updateWrapper.eq(EmailCodeDO::getUserEmail, emailCodeDO.getUserEmail())
                // 设置要更新的字段内容
                .set(EmailCodeDO::getEmailCode, emailCodeDO.getEmailCode());
        log.info("更新邮箱验证码数据库中");
        // 调用 Mapper 的 update 方法执行更新操作
        emailCodeMapper.update(null, updateWrapper);
    }
}
