package com.flashlack.homeofesportsracingsimulatorsettings.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.SettingsGameDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 游戏设置数据对象映射器
 * @author FLASHLACK
 */
@Mapper
public interface SettingsGameMapper extends BaseMapper<SettingsGameDO> {
}
