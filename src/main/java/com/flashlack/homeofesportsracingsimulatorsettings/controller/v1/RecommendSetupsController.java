package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetAccSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetBaseSetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetF124SetupsDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddF124SetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RecommendSetupsService;
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
 * 推荐设置控制器
 *
 * @author FLASHLACK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/recommendSetups")
@CrossOrigin(origins = "*")
public class RecommendSetupsController {
    private final RedisService redisService;
    private final RecommendSetupsService recommendSetupsService;
    private final SettingsService settingsService;

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
     * 管理员添加ACC赛车设置
     *
     * @param request   请求
     * @param roleAlias 角色别名
     * @param getData   添加ACC赛车设置数据
     * @return 是否添加成功
     */
    @PostMapping(value = "/adminAddAccSetups", name = "管理员添加ACC赛车设置")
    public ResponseEntity<BaseResponse<String>> adminAddAccSetups(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestBody AddAccSetupsVO getData
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        //通过权限访问运行添加ACC设置
        settingsService.addAccSetups(userUuid, getData, true);
        return ResultUtil.success("添加成功", "管理员添加推荐赛车设置成功");
    }

    /**
     * 管理员添加F124赛车设置
     *
     * @param request   请求
     * @param roleAlias 角色别名
     * @param getData   添加F124赛车设置数据
     * @return 是否添加成功
     */
    @PostMapping(value = "/adminAddF124Setups", name = "管理员添加F124赛车设置")
    public ResponseEntity<BaseResponse<String>> adminAddF124Setups(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestBody AddF124SetupsVO getData
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        //通过权限访问运行添加F124设置
        settingsService.addF124Setups(userUuid, getData, true);
        return ResultUtil.success("添加成功", "管理员添加推荐赛车设置成功");
    }

    /**
     * 管理员删除赛车设置
     *
     * @param request    请求
     * @param roleAlias  角色别名
     * @param setupsUuid 设置uuid
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/adminDeleteSetups", name = "管理员删除赛车设置")
    public ResponseEntity<BaseResponse<String>> deleteSetups(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestParam String setupsUuid
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        log.info("权限检查完毕");
        //通过权限访问运行删除赛车设置
        recommendSetupsService.deleteSetups(setupsUuid);
        return ResultUtil.success("删除成功", "删除成功");
    }

    /**
     * 用户添加赛车设置
     *
     * @param request    请求
     * @param setupsUuid 赛车设置uuid
     * @param setupsName 赛车设置名称
     * @return 是否添加成功
     */
    @PostMapping(value = "/userAddRecommendSetups", name = "用户添加荐赛车设置")
    public ResponseEntity<BaseResponse<String>> userAddAccRecommendSetups(
            HttpServletRequest request,
            @RequestParam String setupsUuid,
            @RequestParam String setupsName
    ) {
        String userUuid = getUserUuid(request);
        //添加推荐赛车设置
        recommendSetupsService.userAddAccSetups(setupsUuid, setupsName, userUuid);
        return ResultUtil.success("添加成功", "用户添加推荐赛车设置成功");
    }

    /**
     * 获取推荐赛车设置基本信息
     *
     * @param request   请求
     * @param page      页数
     * @param gameName  游戏名称
     * @param trackName 赛道名称
     * @param carName   赛车名称
     * @return 赛车设置
     */
    @GetMapping(value = "/getRecommendBaseSetups", name = "获取推荐赛车设置基本信息")
    public ResponseEntity<BaseResponse<CustomPage<GetBaseSetupsDTO>>> getAccBaseSetups(
            HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam String gameName,
            @RequestParam String trackName,
            @RequestParam String carName
    ) {
        String userUuid = getUserUuid(request);
        CustomPage<GetBaseSetupsDTO> getAccSetupsDtoPage = settingsService
                .getBaseSetups(userUuid, gameName,
                        trackName, carName, page, true);
        return ResultUtil.success("获取赛车设置成功", getAccSetupsDtoPage);
    }

    /**
     * 获取推荐ACC赛车设置
     * @param request 请求
     * @param setupsUuid 赛车设置uuid
     * @return ACC赛车设置
     */
    @GetMapping(value = "/getRecommendACCSetups", name = "获取推荐ACC赛车设置")
    public ResponseEntity<BaseResponse<GetAccSetupsDTO>> getAccSetups(
            HttpServletRequest request,
            @RequestParam String setupsUuid
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        GetAccSetupsDTO getAccSetupsDTO = recommendSetupsService.getRecommendAccSetups(setupsUuid);
        return ResultUtil.success("获取赛车设置成功", getAccSetupsDTO);
    }
    /**
     * 获取推荐F124赛车设置
     * @param request 请求
     * @param setupsUuid 赛车设置uuid
     * @return F124赛车设置
     */
    @GetMapping(value = "/getRecommendF124Setups", name = "获取推荐F124赛车设置")
    public ResponseEntity<BaseResponse<GetF124SetupsDTO>> getF124Setups(
            HttpServletRequest request,
            @RequestParam String setupsUuid
    ) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
        GetF124SetupsDTO getF124SetupsDTO = recommendSetupsService.getRecommendF124Setups(setupsUuid);
        return ResultUtil.success("获取赛车设置成功", getF124SetupsDTO);
    }
}
