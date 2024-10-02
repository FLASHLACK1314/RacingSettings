package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录控制器
 *
 * @author FLASHLACK
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    /**
     * 用户注册
     *
     * @return 是否返回成功
     */
    @RequestMapping("/register")
    public ResponseEntity<BaseResponse<String>> userRegister(
            HttpServletRequest request) {

        return ResultUtil.success("注册成功", "注册成功");
    }

    @RequestMapping("/login")
    public ResponseEntity<BaseResponse<String>> userLogin(
            HttpServletRequest request) {
        return ResultUtil.success("登录成功", "登录成功");
    }
}
