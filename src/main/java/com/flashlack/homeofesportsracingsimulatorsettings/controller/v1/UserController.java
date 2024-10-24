package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeEmailVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangeNickNameVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.ChangePasswordVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UserInformationVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.UserService;
import com.flashlack.homeofesportsracingsimulatorsettings.until.UUIDUtils;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author FLASHLACK
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;
    private final RedisService redisService;

    /**
     * 获取用户信息
     *
     * @param request 请求
     * @return 用户信息
     */
    @GetMapping(value = "/getOwnUserInformation", name = "获取用户信息")
    public ResponseEntity<BaseResponse<UserInformationVO>> getOwnUserInformation(
            HttpServletRequest request
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        UserInformationVO userInformationVO = userService.getUserInformation(userUuid);
        log.info("获取用户信息");
        return ResultUtil.success("获取用户信息成功", userInformationVO);
    }

    /**
     * 用户修改邮箱
     * @param request 请求
     * @param getData 修改邮箱数据VO
     * @return 是否修改成功
     */
    @PostMapping(value = "/userChangeEmail", name = "用户修改邮箱")
    public ResponseEntity<BaseResponse<Void>> userChangeEmail(
            HttpServletRequest request,
            @RequestBody ChangeEmailVO getData
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        log.info("用户修改邮箱");
        userService.checkEmail(userUuid, getData);
        userService.changeEmail(userUuid, getData);
        return ResultUtil.success("修改邮箱成功");
    }

    /**
     * 用户修改密码
     *
     * @param request 请求
     * @param getData 修改密码数据VO
     * @return 是否修改成功
     */
    @PostMapping(value = "/userChangePassword", name = "用户修改密码")
    public ResponseEntity<BaseResponse<Void>> userChangePassword(
            HttpServletRequest request,
            @RequestBody ChangePasswordVO getData
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        log.info("用户修改密码");
        userService.changePassword(userUuid, getData);
        //删除Token
        redisService.deleteTokenFromRedis(userUuid);
        return ResultUtil.success("修改密码成功");
    }

    /**
     * 用户修改昵称
     *
     * @param request  请求
     * @param nickName 修改昵称数据VO
     * @return 是否修改成功
     */
    @PostMapping(value = "/userChangeNickName", name = "用户修改昵称")
    public ResponseEntity<BaseResponse<Void>> userChangeNickName(
            HttpServletRequest request,
            @RequestBody ChangeNickNameVO nickName
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        log.info("用户修改昵称");
        userService.changeNickName(userUuid, nickName);
        return ResultUtil.success("修改昵称成功");
    }

    /**
     * 用户登出
     *
     * @param request 请求
     * @return 是否登出成功
     */
    @DeleteMapping(value = "/userLogout", name = "用户登出")
    public ResponseEntity<BaseResponse<Void>> userLogout(
            HttpServletRequest request
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        log.info("用户登出");
        //删除Token
        redisService.deleteTokenFromRedis(userUuid);
        return ResultUtil.success("登出成功");
    }
}
