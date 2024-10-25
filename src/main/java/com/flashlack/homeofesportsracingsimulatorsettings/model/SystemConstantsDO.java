package com.flashlack.homeofesportsracingsimulatorsettings.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 存放系统信息常量
 *
 * @author FLASHLACK
 */
@Data
@TableName("system_constants")
@Accessors(chain = true)
@SuppressWarnings("unused")
public class SystemConstantsDO {
    @TableId(type = IdType.NONE)
    String key;
    String value;
}
