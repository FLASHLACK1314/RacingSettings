package com.flashlack.homeofesportsracingsimulatorsettings.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 登录信息对象
 * @author FLASHLACK
 */
@Data
@TableName("settings_user")
@SuppressWarnings("unused")
public class UserDO {
    String userUuid;
    String roleUuid;
    String userPassword;
    String userEmail;
    String nickName;
}
