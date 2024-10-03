package com.flashlack.homeofesportsracingsimulatorsettings.controller.v3;

import com.flashlack.homeofesportsracingsimulatorsettings.service.MailService;
import com.xlf.utility.BaseResponse;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.ResultUtil;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 邮件控制器
 *
 * @author FLASHLACK
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@Validated
@RequestMapping("/v3/mail")
public class MailController {
    private final MailService mailService;

    /**
     * 发送邮件测试
     *
     * @return 是否发送成功
     */
    @GetMapping(value = "/sendTest", name = "发送邮件测试")
    public @NotNull ResponseEntity<BaseResponse<String>> sendMailTest() {
        log.info("发送邮箱测试邮件");
        mailService.sendMail();
        return ResultUtil.success("测试邮箱发送成功", "测试邮箱发送成功");
    }

    /**
     * 发送邮件验证码
     *
     * @param to 邮箱地址
     * @return 是否发送成功
     */
    @GetMapping(value = "/sendEmailCode", name = "发送邮件验证码")
    public @NotNull ResponseEntity<BaseResponse<String>> sendEmailCode(
            @RequestBody String to
    ) {
        //检查邮箱是否符合规范
        if (!to.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw new BusinessException("邮箱格式错误", ErrorCode.PARAMETER_ERROR);
        }
        log.info("发送邮箱验证码");
        mailService.sendMailCode(to);
        return ResultUtil.success("邮箱验证码发送成功", "邮箱验证码发送成功");
    }
}
