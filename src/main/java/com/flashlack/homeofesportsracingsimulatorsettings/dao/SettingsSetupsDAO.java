package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsSetupsMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsSetupsDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 设置信息实体类
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SettingsSetupsDAO extends ServiceImpl<SettingsSetupsMapper, SettingsSetupsDO>
        implements IService<SettingsSetupsDO> {
}
