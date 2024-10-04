package com.flashlack.homeofesportsracingsimulatorsettings.model.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 注册信息缓存
 *
 * @author FLASHLACK
 */
@Data
@SuppressWarnings("unused")
public class RegisterVO {
    @NotBlank(message = "邮箱不能为空")
    @Pattern(regexp = "^[\\w-]+@[\\w-]+(\\.[\\w-]+)+$", message = "请输入有效的邮箱地址")
    String userEmail;
    @NotBlank(message = "验证码不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码必须是6位数字")
    String emailCode;
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,18}$",
            message = "密码必须包含6-18个字符，并且必须包含至少一个字母和一个数字")
    String userPassword;
    String confirmPassword;
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9.]{1,15}$",
            message = "昵称只能包含中文、英文、数字或英文点号，且长度在1-15个字符之间")
    String nickName;
}
