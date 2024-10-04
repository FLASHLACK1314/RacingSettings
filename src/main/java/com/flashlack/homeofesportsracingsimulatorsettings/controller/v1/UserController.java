package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UserInformationVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.UserService;
import com.flashlack.homeofesportsracingsimulatorsettings.until.JwtUtil;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 *
 * @author FLASHLACK
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 获取用户信息
     *
     * @param token 用户Token
     * @return 用户信息
     */
    @GetMapping(value = "/getOwnUserInformation", name = "获取用户信息")
    public ResponseEntity<BaseResponse<UserInformationVO>> getOwnUserInformation(
            @RequestHeader String token
    ) {
        //解析Token
        String userUuid = jwtUtil.getUserUuidFromToken(token);
        UserInformationVO userInformationVO = userService.getUserInformation(userUuid);
        log.info("获取用户信息");
        return ResultUtil.success("获取用户信息成功", userInformationVO);
    }
}
