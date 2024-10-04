package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.SystemConstantsMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.SystemConstantsDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 系统常量DAO
 *
 * @author FLASHLACK
 */
@Slf4j
@Repository
public class SystemConstantsDAO extends ServiceImpl<SystemConstantsMapper, SystemConstantsDO>
        implements IService<SystemConstantsDO> {
}
