package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddAccSetupsVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddF124SetupsVO;
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
 * 推荐设置控制器
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
     * @param request 请求
     * @param roleAlias 角色别名
     * @param getData 添加ACC赛车设置数据
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
        recommendSetupsService.adminAddAccSetups(getData,userUuid);
        return ResultUtil.success("添加成功", "管理员添加推荐赛车设置成功");
    }
    /**
     * 管理员添加F124赛车设置
     * @param request 请求
     * @param roleAlias 角色别名
     * @param getData 添加F124赛车设置数据
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
        recommendSetupsService.adminAddF124Setups(getData,userUuid);
        return ResultUtil.success("添加成功", "管理员添加推荐赛车设置成功");
    }

    /**
     * 删除赛车设置
     * @param request 请求
     * @param roleAlias 角色别名
     * @param setupsUuid 设置uuid
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/deleteSetups", name = "删除赛车设置")
    public ResponseEntity<BaseResponse<String>> deleteSetups(
            HttpServletRequest request,
            @RequestParam String roleAlias,
            @RequestParam String setupsUuid
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        //通过权限访问运行删除赛车设置
        recommendSetupsService.deleteSetups(setupsUuid);
        return ResultUtil.success("删除成功", "删除成功");
    }
}
