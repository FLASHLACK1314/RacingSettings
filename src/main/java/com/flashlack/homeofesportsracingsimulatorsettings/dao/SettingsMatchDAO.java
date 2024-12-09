package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsMatchMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsMatchDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 比赛设置数据访问对象
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SettingsMatchDAO extends ServiceImpl<SettingsMatchMapper, SettingsMatchDO>
        implements IService<SettingsMatchDO> {
}
