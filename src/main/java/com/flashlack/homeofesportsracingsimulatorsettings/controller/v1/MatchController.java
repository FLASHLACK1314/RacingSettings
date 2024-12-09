package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetMatchListDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddMatchVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateMatchVO;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

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
     * 检查用户uuid
     *
     * @param request 请求
     */
    private void checkUserUuid(HttpServletRequest request) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
    }

    /**
     * 管理员添加比赛
     *
     * @param request   请求
     * @param roleAlias 角色别名
     * @param getData   添加比赛数据
     * @return 是否添加成功
     */
    @PostMapping(value = "/adminAddMatch", name = "管理员添加比赛")
    public ResponseEntity<BaseResponse<String>> adminAddMatch(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestBody AddMatchVO getData
    ) throws ParseException {
        String userUuid = getUserUuid(request);
        recommendSetupsService.checkRole(userUuid, roleAlias);
        matchService.checkAddMatchData(getData);
        log.info("管理员添加比赛数据：{}", getData);
        matchService.adminAddMatch(getData);
        return ResultUtil.success("添加比赛成功", "添加比赛成功");
    }

    /**
     * 获取比赛列表
     *
     * @param request   请求
     * @param page      页数
     * @param gameName  游戏名
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 比赛列表
     */
    @GetMapping(value = "/getMatchList", name = "获取比赛列表")
    public ResponseEntity<BaseResponse<CustomPage<GetMatchListDTO>>> getMatchList(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam String gameName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") String startTime,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") String endTime
    ) throws ParseException {
        checkUserUuid(request);
        log.info("获取比赛列表");
        CustomPage<GetMatchListDTO> matchList = matchService.getMatchList(page, gameName, startTime, endTime);
        return ResultUtil.success("获取比赛列表成功", matchList);
    }

    /**
     * 管理员删除比赛
     *
     * @param request   请求
     * @param roleAlias 角色别名
     * @param matchUuid 比赛uuid
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/adminDeleteMatch", name = "管理员删除比赛")
    public ResponseEntity<BaseResponse<String>> adminDeleteMatch(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestParam String matchUuid
    ) {
        String userUuid = getUserUuid(request);
        recommendSetupsService.checkRole(userUuid, roleAlias);
        log.info("管理员删除比赛");
        matchService.adminDeleteMatch(matchUuid);
        return ResultUtil.success("删除比赛成功", "删除比赛成功");
    }

    /**
     * 管理员更新比赛
     *
     * @param request   请求
     * @param roleAlias 角色别名
     * @param getData   更新比赛数据
     * @return 是否更新成功
     */
    @PostMapping(value = "/adminUpdateMatch", name = "管理员更新比赛")
    public ResponseEntity<BaseResponse<String>> adminUpdateMatch(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestBody UpdateMatchVO getData
    ) throws ParseException {
        String userUuid = getUserUuid(request);
        recommendSetupsService.checkRole(userUuid, roleAlias);
        matchService.checkUpdateMatchVO(getData);
        log.info("管理员更新比赛数据：{}", getData);
        matchService.adminUpdateMatch(getData);
        return ResultUtil.success("更新比赛成功", "更新比赛成功");
    }
}
