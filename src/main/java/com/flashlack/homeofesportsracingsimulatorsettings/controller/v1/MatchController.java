package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddMatchVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.MatchService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RecommendSetupsService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
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
 * 比赛控制器
 *
 * @author FLASHLACK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/match")
@CrossOrigin(origins = "*")
public class MatchController {
    private final RedisService redisService;
    private final MatchService matchService;
    private final RecommendSetupsService recommendSetupsService;

    /**
     * 获取用户uuid
     *
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
     * 管理员添加比赛
     * @param request 请求
     * @param roleAlias 角色别名
     * @param getData 添加比赛数据
     * @return 是否添加成功
     */
    @PostMapping(value = "/adminAddMatch", name = "管理员添加比赛")
    public ResponseEntity<BaseResponse<String>> adminAddMatch(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestBody AddMatchVO getData
    ) {
        String userUuid = getUserUuid(request);
        recommendSetupsService.checkRole(userUuid, roleAlias);

        matchService.checkAddMatchData(getData);
        log.info("管理员添加比赛数据：{}", getData);
        matchService.adminAddMatch(getData);
        return ResultUtil.success("添加比赛成功", "添加比赛成功");
    }
}
