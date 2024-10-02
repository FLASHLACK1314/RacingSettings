package com.flashlack.homeofesportsracingsimulatorsettings.controller.v3;

import com.xlf.utility.BaseResponse;
import com.xlf.utility.ResultUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 邮件控制器
 *
 * @author FLASHLACK
 */
@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v3/mail")
public class MailController {

    /**
     * 发送邮件
     *
     * @return 是否发送成功
     */
    @GetMapping("/send")
    public @NotNull ResponseEntity<BaseResponse<String>> sendMail() {
        log.info("send mail");
        return ResultUtil.success("发送成功", "发送成功");
    }
}
