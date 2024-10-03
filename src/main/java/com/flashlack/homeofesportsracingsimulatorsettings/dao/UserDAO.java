package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.UserMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 用户DAO
 * @author FLASHALCK
 */
@Repository
@Slf4j
public class UserDAO extends ServiceImpl<UserMapper, UserDO> implements IService<UserDO> {
}
