package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsMatchRegistrationMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsMatchRegistrationDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 报名记录数据访问对象
 */
@Slf4j
@Repository
public class SettingsMatchRegistrationDAO extends ServiceImpl<SettingsMatchRegistrationMapper, SettingsMatchRegistrationDO>
        implements IService<SettingsMatchRegistrationDO> {
}
