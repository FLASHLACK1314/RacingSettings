package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.RegisterVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private final AuthService authService;
    /**
     * 用户注册
     * @param getData 注册信息
     * @return 是否返回成功
     */
    @PostMapping(value = "/register",name = "用户注册")
    public ResponseEntity<BaseResponse<String>> userRegister(
            @RequestBody RegisterVO getData
            ) {
        //检查数据
        authService.checkRegisterData(getData);
        //进行注册
        authService.register(getData);
        return ResultUtil.success("注册成功", "注册成功");
    }

    /**
     * 用户登录
     * @param request 请求
     * @return 是否登录成功
     */
    @RequestMapping(value = "/login",name = "用户登录")
    public ResponseEntity<BaseResponse<String>> userLogin(
            HttpServletRequest request) {
        return ResultUtil.success("登录成功", "登录成功");
    }
}
