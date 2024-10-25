package com.flashlack.homeofesportsracingsimulatorsettings.controller.v2;

import com.flashlack.homeofesportsracingsimulatorsettings.util.JwtUtil;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类
 *
 * @author FLASHLACK
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v2/test")
public class TestController {
    private final JwtUtil jwtUtil;

    /**
     * JWT令牌测试
     *
     * @param request 请求
     * @return 测试结果
     */
    @PostMapping(value = "/test", name = "测试")
    public @NotNull ResponseEntity<BaseResponse<String>> test(
            HttpServletRequest request
    ) {
        //获取用户令牌
        String token = request.getHeader("Authorization");
        log.info("测试");
        log.info("token: {}", token);
        if (jwtUtil.validateToken(token)) {
            log.info("token验证通过");
        } else {
            log.info("token验证失败");
        }
        if (jwtUtil.getUserUuidFromToken(token) != null) {
            log.info("userUuid: {}", jwtUtil.getUserUuidFromToken(token));
            return ResultUtil.success("JWT令牌测试成功", "JWT令牌测试成功");
        } else {
            throw new BusinessException("无效的令牌", ErrorCode.HEADER_ERROR);
        }
    }
}
