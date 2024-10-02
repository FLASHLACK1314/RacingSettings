package com.flashlack.homeofesportsracingsimulatorsettings.logic;

import com.flashlack.homeofesportsracingsimulatorsettings.dao.EmailCodeDAO;
import com.flashlack.homeofesportsracingsimulatorsettings.model.EmailCodeDO;
import com.flashlack.homeofesportsracingsimulatorsettings.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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


    @Override
    public void sendMail() {
        // 创建邮件
        String subject = "邮件标题";
        String content = "邮件内容";
        String to = "1550909467@qq.com";
        String from = "17372855625@163.com";
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setText(content);
        mail.setTo(to);
        mail.setFrom(from);
        log.info("请稍等正在发送邮件");
        // 发送邮件
        javaMailSender.send(mail);
    }


    @Override
    public void sendMailCode(String to, String userUuid) {
        // 创建邮件
        String subject = "邮箱验证码";
        String code = generateVerificationCode();
        String content = "您的验证码是：" + code + "，5 分钟后失效。";
        //存入数据库中
        if (userUuid != null) {
            //存入数据库
            throw new RuntimeException("未实现");
        } else {
            //只是存入验证码
            EmailCodeDO emailCodeDO = new EmailCodeDO();
            emailCodeDO.setUserEmail(to)
                    .setUserUuid(null)
                    .setEmailCode(code)
                    .setCreateTime(null)
                    .setExpireTime(null);
            log.info("{}", emailCodeDO.getEmailCode());
            log.info("{}", emailCodeDO.getUserEmail());
            log.info("存入数据库中");
            //存入数据库
            emailCodeDAO.save(emailCodeDO);
        }
        String from = "17372855625@163.com";
        log.info("请稍等正在发送邮件验证码:{}", code);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setSubject(subject);
        mail.setText(content);
        mail.setTo(to);
        mail.setFrom(from);
        log.info("请稍等正在发送邮件验证码");
        // 发送邮件
        javaMailSender.send(mail);
    }

}
