package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsTeachingMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsTeachingDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
/**
 * 教学设置DAO
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SettingsTeachingDAO extends ServiceImpl<SettingsTeachingMapper, SettingsTeachingDO>
        implements IService<SettingsTeachingDO> {

}
