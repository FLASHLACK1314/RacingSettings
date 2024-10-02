package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.EmailCodeMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 邮箱验证码DAO
 *
 * @author FLASHLACK
 */
@Repository
@Slf4j
public class EmailCodeDAO extends ServiceImpl<EmailCodeMapper, EmailCodeDO> implements IService<EmailCodeDO> {

}
