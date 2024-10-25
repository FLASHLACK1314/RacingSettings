package com.flashlack.homeofesportsracingsimulatorsettings.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 角色对象
 *
 * @author FLASHLACK
 */
@Data
@TableName("settings_role")
@SuppressWarnings("unused")
public class RoleDO {
    @TableId(type = IdType.NONE)
    String roleUuid;
    String roleAlias;
    String rolePermission;
}
