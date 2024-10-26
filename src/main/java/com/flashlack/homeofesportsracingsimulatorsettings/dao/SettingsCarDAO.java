package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsCarMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsCarDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 车辆设置DAO
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SettingsCarDAO extends ServiceImpl<SettingsCarMapper, SettingsCarDO>
        implements IService<SettingsCarDO> {
}
