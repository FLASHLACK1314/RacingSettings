package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddACCSetupsVO;
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
     * 添加ACC赛车设置
     * @return 是否添加成功
     */
    @PostMapping(value = "/addAccSetups", name = "添加ACC赛车设置")
    public ResponseEntity<BaseResponse<String>> addSetups(
            HttpServletRequest request,
            @RequestBody AddACCSetupsVO getData
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        log.info("添加赛车设置数据：{}", getData);
        settingsService.addAccSetups(userUuid,getData);
        return ResultUtil.success("添加赛车设置成功", "添加赛车设置成功");
    }
    /**
     * 获取ACC赛车设置
     * @return ACC赛车设置
     */
    @GetMapping(value = "/getAccSetups", name = "获取ACC赛车设置")
    public ResponseEntity<BaseResponse<String>> getSetups(
            HttpServletRequest request
    ) {

        return ResultUtil.success("获取赛车设置成功", "获取赛车设置成功");
    }
    /**
     * 删除ACC赛车设置
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/deleteAccSetups", name = "删除赛车设置")
    public ResponseEntity<BaseResponse<String>> deleteSetups(
            HttpServletRequest request
    ) {
        return ResultUtil.success("删除赛车设置成功", "删除赛车设置成功");
    }
}
