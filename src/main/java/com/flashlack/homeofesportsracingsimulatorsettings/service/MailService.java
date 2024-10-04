package com.flashlack.homeofesportsracingsimulatorsettings.service;

/**
 * 邮箱验证接口
 * @author FLASHLACK
 */

public interface MailService {
    /**
     * 发送邮件验证码
     * @param to 邮箱地址
     */
    void sendMailCode
    (String to);
}
