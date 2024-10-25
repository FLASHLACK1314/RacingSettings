package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.UserMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户DAO
 *
 * @author FLASHALCK
 */
@Repository
@Slf4j
public class UserDAO extends ServiceImpl<UserMapper, UserDO>
        implements IService<UserDO> {
    private final UserMapper userMapper;

    public UserDAO(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * 通过用户UUID更新用户密码
     *
     * @param userDO 用户实体类
     */
    public void updateUserPasswordByUuid(UserDO userDO) {
        LambdaUpdateWrapper<UserDO> updateWrapper = new LambdaUpdateWrapper<>();
        // 设置更新条件：根据主键 ID 来匹配
        updateWrapper.eq(UserDO::getUserUuid, userDO.getUserUuid())
                // 设置要更新的字段内容
                .set(UserDO::getUserPassword, userDO.getUserPassword());
        userMapper.update(null, updateWrapper);
    }

    public void updateUserEmailByUuid(UserDO userDO) {
        this.lambdaUpdate().eq(UserDO::getUserUuid, userDO.getUserUuid())
                .set(UserDO::getUserEmail, userDO.getUserEmail())
                .update();
        log.info("更新用户邮箱中");
    }
}
