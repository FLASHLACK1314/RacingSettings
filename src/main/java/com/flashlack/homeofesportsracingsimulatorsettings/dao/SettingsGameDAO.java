package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SettingsGameMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsGameDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 游戏设置数据对象映射器
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SettingsGameDAO extends ServiceImpl<SettingsGameMapper, SettingsGameDO>
        implements IService<SettingsGameDO> {
}
