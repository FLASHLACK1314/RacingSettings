package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsTrackMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsTrackDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 赛道设置DAO
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SettingsTrackDAO extends ServiceImpl<SettingsTrackMapper, SettingsTrackDO>
        implements IService<SettingsTrackDO> {
}
