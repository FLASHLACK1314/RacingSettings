package com.flashlack.homeofesportsracingsimulatorsettings.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 邮箱验证码数据对象
 *
 * @author FLASHLACK
 */
@Data
@TableName("email_code")
@SuppressWarnings("unused")
@Accessors(chain = true)
public class EmailCodeDO {
    @TableId(type = IdType.NONE)
    String userEmail;
    String userUuid;
    String emailCode;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT)
    LocalDateTime expireTime;
}
