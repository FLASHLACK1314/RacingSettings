package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccBaseSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.SettingsService;
import com.flashlack.homeofesportsracingsimulatorsettings.util.UUIDUtils;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 赛车设置相关控制器
 * @author FLASHLACK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/settings")
@CrossOrigin(origins = "*")
public class SetupsController {
    private final SettingsService settingsService;
    private final RedisService redisService;

    /**
     * 获取用户uuid
     * @param request 请求
     * @return 用户uuid
     */
    private @NotNull String getUserUuid(HttpServletRequest request) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        return userUuid;
    }
    /**
     * 添加ACC赛车设置
     * @return 是否添加成功
     */
    @PostMapping(value = "/addAccSetups", name = "添加ACC赛车设置")
    public ResponseEntity<BaseResponse<String>> addSetups(
            HttpServletRequest request,
            @RequestBody AddAccSetupsVO getData
    ) {
        String userUuid = getUserUuid(request);
        log.info("添加ACC赛车设置数据：{}", getData);
        settingsService.addAccSetups(userUuid,getData);
        return ResultUtil.success("添加赛车设置成功", "添加赛车设置成功");
    }

    /**
     * 获取ACC赛车设置
     * @return ACC赛车设置
     */
    @GetMapping(value = "/getAccBaseSetups", name = "获取ACC赛车设置基本信息")
    public ResponseEntity<BaseResponse<CustomPage<GetAccBaseSetupsDTO>>> getAccBaseSetups(
            HttpServletRequest request,
            @RequestParam (value = "page",defaultValue = "1")Integer page,
            @RequestParam String gameName,
            @RequestParam String trackName,
            @RequestParam String carName
    ) {
        String userUuid = getUserUuid(request);
        CustomPage<GetAccBaseSetupsDTO> getAccSetupsDtoPage = settingsService
                .getAccBaseSetups(userUuid, gameName,
                trackName, carName, page);
        return ResultUtil.success("获取赛车设置成功", getAccSetupsDtoPage);
    }

    /**
     * 获取ACC详细赛车设置
     * @return ACC赛车设置
     */
    @GetMapping(value = "/getAccSetups", name = "获取ACC赛车设置")
    public @NotNull ResponseEntity<BaseResponse<GetAccSetupsDTO>>  getAccSetups(
            HttpServletRequest request,
            @RequestParam String setupsUuid
    ) {
        String userUuid = getUserUuid(request);
        GetAccSetupsDTO getAccSetupsDto = settingsService.getAccSetups(userUuid, setupsUuid);
        return ResultUtil.success("获取赛车设置成功",getAccSetupsDto);
    }

    /**
     * 删除ACC赛车设置
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/deleteAccSetups", name = "删除赛车设置")
    public ResponseEntity<BaseResponse<String>> deleteSetups(
            HttpServletRequest request,
            @RequestParam String setupsUuid
    ) {
        String userUuid = getUserUuid(request);
        log.info("删除ACC赛车设置数据：{}", setupsUuid);
        settingsService.deleteAccSetups(userUuid, setupsUuid);
        return ResultUtil.success("删除赛车设置成功", "删除赛车设置成功");
    }

    /**
     * 更新ACC赛车设置
     * @param request 请求
     * @param getData 更新赛车设置数据
     * @return 是否更新成功
     */
    @PostMapping(value = "/updateAccSetups", name = "更新ACC赛车设置")
    public ResponseEntity<BaseResponse<String>> updateSetups(
            HttpServletRequest request,
            @RequestBody UpdateAccSetupsVO getData
    ) {
        String userUuid = getUserUuid(request);
        log.info("更新ACC赛车设置数据：{}", getData);
        settingsService.updateAccSetups(userUuid,getData);
        return ResultUtil.success("更新赛车设置成功", "更新赛车设置成功");
    }
}
