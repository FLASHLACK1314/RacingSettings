package com.flashlack.homeofesportsracingsimulatorsettings.controller.v2;

import com.flashlack.homeofesportsracingsimulatorsettings.until.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制类
 * @author FLASHLACK
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("v2/test")
public class TestController {
    private final JwtUtil jwtUtil;
    /**
     * 测试
     * @param token token
     */
    @PostMapping(value = "/test", name = "测试")
    public void test(
            @RequestHeader String token
    ) {
        log.info("测试");
        log.info("token: {}", token);
        if (jwtUtil.validateToken(token)) {
            log.info("token验证通过");
        } else {
            log.info("token验证失败");
        }
        if (jwtUtil.getUserUuidFromToken(token) != null) {
            log.info("userUuid: {}", jwtUtil.getUserUuidFromToken(token));
        }
    }
}
