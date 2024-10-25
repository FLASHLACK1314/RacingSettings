package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 登录信息对象
 *
 * @author FLASHLACK
 */
@Data
@TableName("settings_user")
@Accessors(chain = true)
@SuppressWarnings("unused")
public class UserDO {
    @TableId(type = IdType.NONE)
    String userUuid;
    String roleUuid;
    String userPassword;
    String userEmail;
    String nickName;
}
