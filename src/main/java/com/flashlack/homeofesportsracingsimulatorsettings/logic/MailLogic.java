package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.EmailCodeDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.entity.EmailCodeDO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.MailService;
import com.xlf.utility.ErrorCode;
import com.xlf.utility.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 邮箱验证逻辑
 *
 * @author FLASHLACK
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MailLogic implements MailService {
    private final JavaMailSender javaMailSender;
    private final EmailCodeDAO emailCodeDAO;

    /**
     * 生成验证码
     *
     * @return 验证码
     */
    public String generateVerificationCode() {
        Random random = new Random();
        // 使用 String.format 将数字格式化为 6 位，不足 6 位时自动补零
        return String.format("%06d", random.nextInt(1000000));
    }

    /**
     * 发送邮箱验证码方法
     *
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMailCode(String to, String subject, String content) {
        // 发件人邮箱地址
        String from = "flashlack1314@163.com";
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setText(content);
        mail.setTo(to);
        mail.setFrom(from);
        // 日志记录
        log.info("请稍等，正在发送邮件验证码至: {}", to);
        // 发送邮件
        javaMailSender.send(mail);
        log.info("邮件发送成功: {}", to);
    }

    @Async
    @Override
    public void sendMailCode(String to) {
        // 创建邮件
        String subject = "邮箱验证码";
        String code = generateVerificationCode();
        String content = "您的验证码是：" + code + "，5 分钟后失效。";
        //检查用户邮箱是否存在
        EmailCodeDO emailFindCodeDO = emailCodeDAO.lambdaQuery()
                .eq(EmailCodeDO::getUserEmail, to).one();
        LocalDateTime now = LocalDateTime.now();
        if (emailFindCodeDO != null) {
            //检查是否可以重发
            if (!emailFindCodeDO.getCreateTime().plusMinutes(2).isBefore(now)) {
                //发送时间延迟2分钟后依然比现在早，静止发送
                throw new BusinessException("请勿频繁发送验证码", ErrorCode.OPERATION_ERROR);
            } else {
                //更新数据库
                emailFindCodeDO.setEmailCode(code);
                emailCodeDAO.updateEmailCodeByEmail(emailFindCodeDO);
                sendSimpleMailCode(to, subject, content);
            }
        } else {
            //存入数据库中
            //只是存入验证码
            EmailCodeDO emailCodeDO = new EmailCodeDO();
            emailCodeDO.setUserEmail(to)
                    .setEmailCode(code);
            log.info("存入邮箱验证码数据库中");
            //存入数据库
            emailCodeDAO.save(emailCodeDO);
            sendSimpleMailCode(to, subject, content);
        }
    }

}
