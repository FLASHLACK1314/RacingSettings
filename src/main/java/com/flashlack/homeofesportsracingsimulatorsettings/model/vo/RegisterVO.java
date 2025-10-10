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
            message = "密码需要包含6-18位字符，且至少含有一个字母和一个数字")
    String userPassword;
    String confirmPassword;
    @NotBlank(message = "昵称不能为空")
    @Pattern(regexp = "^[\\u4e00-\\u9fa5a-zA-Z0-9.]{1,15}$",
            message = "昵称只能包含中文、英文、数字或英文点号，长度需在1-15个字符之间")
    String nickName;
    @NotBlank(message = "请选择角色类型")
    @Pattern(regexp = "^(USER|ORGANIZER)$", message = "角色类型必须为 USER 或 ORGANIZER")
    String roleType;
}
