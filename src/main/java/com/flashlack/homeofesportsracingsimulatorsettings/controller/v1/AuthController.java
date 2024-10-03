package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.LoginVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.RegisterVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.flashlack.homeofesportsracingsimulatorsettings.until.JwtUtil;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 *
 * @author FLASHLACK
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param getData 注册信息
     * @return 是否返回成功
     */
    @PostMapping(value = "/register", name = "用户注册")
    public ResponseEntity<BaseResponse<String>> userRegister(
            @RequestBody @Valid RegisterVO getData
    ) {
        //检查数据
        authService.checkRegisterData(getData);
        //进行注册
        authService.register(getData);
        return ResultUtil.success("注册成功", "注册成功");
    }

    /**
     * 用户登录
     *
     * @param getData 登录信息
     * @return 是否登录成功, 成功则返回加密后的Token
     */
    @PostMapping(value = "/login", name = "用户登录")
    public ResponseEntity<BaseResponse<String>> userLogin(
            @Valid @RequestBody LoginVO getData) {
        //检查数据
        String userUuid = authService.checkLoginData(getData);
        String token = jwtUtil.generateToken(userUuid);
        return ResultUtil.success("登录成功", token);
    }
}
