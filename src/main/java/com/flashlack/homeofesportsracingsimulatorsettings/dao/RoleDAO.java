package com.flashlack.homeofesportsracingsimulatorsettings.dao;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flashlack.homeofesportsracingsimulatorsettings.mapper.RoleMapper;
import com.flashlack.homeofesportsracingsimulatorsettings.model.RoleDO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 角色DAO
 * @author FLASHLACK
 */
@Repository
@RequiredArgsConstructor
public class RoleDAO extends ServiceImpl<RoleMapper, RoleDO> implements IService<RoleDO> {
}
