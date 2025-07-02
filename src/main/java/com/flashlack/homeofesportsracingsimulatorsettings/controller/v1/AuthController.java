package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetUserLoginDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.FindPasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.LoginVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.RegisterVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.AuthService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.JwtUtil;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 登录控制器
 *
 * @author FLASHLACK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    private final RedisService redisService;

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
    public ResponseEntity<BaseResponse<GetUserLoginDTO>> userLogin(
            @Valid @RequestBody LoginVO getData) {
        //检查数据
        String userUuid = authService.checkLoginData(getData);
        String token = JwtUtil.generateToken(userUuid);
        //存入Redis
        redisService.saveTokenToRedis(userUuid, token);
        //设置返回信息
        GetUserLoginDTO getUserLoginDTO = authService.creatLoginBackInformation(userUuid,token);
        return ResultUtil.success("登录成功", getUserLoginDTO);
    }
    /**
     * 用户找回修改密码
     * @param getData 找回密码信息
     * @return 是否找回成功
     */
    @PostMapping(value = "/findPassword", name = "用户找回密码")
    public ResponseEntity<BaseResponse<String>> findPassword(
            @Valid @RequestBody FindPasswordVO getData) {
        //检查数据
        authService.checkAndFindPasswordData(getData);
        return ResultUtil.success("修改密码成功请重新登录", "修改密码成功请重新登录");
    }
}
