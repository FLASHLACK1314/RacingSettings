package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户修改密码缓存
 *
 * @author FLASHLACK
 */
@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class ChangePasswordVO {
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,18}$",
            message = "密码必须包含6-18个字符，并且必须包含至少一个字母和一个数字")
    String oldPassword;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,18}$",
            message = "密码必须包含6-18个字符，并且必须包含至少一个字母和一个数字")
    String newPassword;
    String confirmPassword;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    String emailCode;
}
