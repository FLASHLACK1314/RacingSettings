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
import org.springframework.web.bind.annotation.*;

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
@CrossOrigin(origins = "*")
public class MailController {
    private final MailService mailService;


    /**
     * 发送邮件验证码
     *
     * @param email 邮箱地址
     * @return 是否发送成功
     */
    @PostMapping(value = "/sendEmailCode", name = "发送邮件验证码")
    public @NotNull ResponseEntity<BaseResponse<String>> sendEmailCode(
            @RequestParam("email") String email
    ) {
        //检查邮箱是否符合规范
        if (!email.matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw new BusinessException("邮箱格式错误", ErrorCode.PARAMETER_ERROR);
        }
        log.info("发送邮箱验证码");
        mailService.sendMailCode(email);
        return ResultUtil.success("邮箱验证码发送成功", "邮箱验证码发送成功");
    }
}
