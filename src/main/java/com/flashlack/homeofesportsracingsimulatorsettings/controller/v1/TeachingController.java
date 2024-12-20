package com.flashlack.homeofesportsracingsimulatorsettings.controller.v1;

import com.flashlack.homeofesportsracingsimulatorsettings.model.CustomPage;
import com.flashlack.homeofesportsracingsimulatorsettings.model.DTO.GetTeachingDTO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.AddTeachingVO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.vo.UpdateTeachingVO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RecommendSetupsService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.RedisService;
import com.flashlack.homeofesportsracingsimulatorsettings.service.TeachingService;
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
 * 教学控制器
 *
 * @author FLASHLACK
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/teaching")
@CrossOrigin(origins = "*")
public class TeachingController {
    private final TeachingService teachingService;
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

    private void checkUserUuid(HttpServletRequest request) {
        String userUuid = UUIDUtils.getUuidByRequest(request);
        if (redisService.getTokenFromRedis(userUuid) == null) {
            throw new BusinessException("未登录", ErrorCode.HEADER_ERROR);
        }
    }

    /**
     * 管理员添加教程
     *
     * @param roleAlias 角色别名
     * @param getData   添加教程数据
     * @param request   请求
     * @return 是否添加成功
     */
    @PostMapping(value = "/adminAddTeaching", name = "管理员添加教程")
    public ResponseEntity<BaseResponse<String>> adminAddTeaching(
            @RequestParam String roleAlias,
            @RequestBody AddTeachingVO getData,
            HttpServletRequest request
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        //检查数据
        teachingService.checkAddTeachingData(getData);
        //添加教程
        teachingService.adminAddTeaching(userUuid, getData);
        return ResultUtil.success("添加赛车设置成功", "添加赛车设置成功");
    }

    /**
     * 管理员删除教程
     *
     * @param roleAlias    角色别名
     * @param teachingUuid 教程uuid
     * @param request      请求
     * @return 是否删除成功
     */
    @DeleteMapping(value = "/adminDeleteTeaching", name = "管理员删除教程")
    public ResponseEntity<BaseResponse<String>> adminDeleteTeaching(
            @RequestParam String roleAlias,
            @RequestParam String teachingUuid,
            HttpServletRequest request
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        //删除教程
        teachingService.adminDeleteTeaching(teachingUuid);
        return ResultUtil.success("删除教程成功", "删除教程成功");
    }

    /**
     * 管理员修改教程
     *
     * @param roleAlias 角色别名
     * @param getData   修改教程数据
     * @param request   请求
     * @return 是否修改成功
     */
    @PostMapping(value = "/adminUpdateTeaching", name = "管理员修改教程")
    public ResponseEntity<BaseResponse<String>> adminUpdateTeaching(
            @RequestParam String roleAlias,
            @RequestBody UpdateTeachingVO getData,
            HttpServletRequest request
    ) {
        String userUuid = getUserUuid(request);
        //检查用户权限
        recommendSetupsService.checkRole(userUuid, roleAlias);
        //检查数据
        teachingService.checkUpdateTeachingData(getData);
        //添加教程
        teachingService.adminUpdateTeaching(userUuid, getData);
        return ResultUtil.success("修改教学设置成功", "修改教学设置成功");
    }

    /**
     * 获取教程
     *
     * @param gameName  游戏别名
     * @param trackName 赛道别名
     * @param carName   车辆别名
     * @param request   请求
     * @return 教程DTO
     */
    @GetMapping(value = "/getBaseTeaching", name = "获取教程")
    public ResponseEntity<BaseResponse<CustomPage<GetTeachingDTO>>> getTeaching(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam String gameName,
            @RequestParam String trackName,
            @RequestParam String carName,
            HttpServletRequest request
    ) {
        checkUserUuid(request);
        //获取教程
        CustomPage<GetTeachingDTO> getTeachingCustomPage = teachingService.getTeaching(gameName, trackName, carName, page);
        return ResultUtil.success("获取教程成功", getTeachingCustomPage);
    }
}
