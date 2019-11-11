package com.jeebud.core.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: </p>
 * <p>Copyright (c) www.jeebud.com Inc. All Rights Reserved.</p>
 *
 * @author Tanxh(itfuyun@gmail.com)
 */
@Component
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;
    /**
     * 模板引擎对象
     */
    @Autowired
    private TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.nickname}")
    private String nickname;
    /**
     * 发送带附件的模板邮件
     *
     * @param to
     * @param subject
     * @param templatePath
     * @param data
     * @param fileList
     */
    public void sendTemplateMail(String to, String subject, String templatePath, Map data, List<File> fileList) {
        String content = getTemplateHtml(data, templatePath);
        sendHtmlMail(to, subject, content, fileList);

    }

    /**
     * 发送模板邮件
     *
     * @param to
     * @param subject
     * @param templatePath
     * @param data
     */
    public void sendTemplateMail(String to, String subject, String templatePath, Map data) {
        sendTemplateMail(to, subject, templatePath, data, null);

    }

    /**
     * html邮件
     *
     * @param to      接收人
     * @param subject 主题
     * @param content 内容
     */
    public void sendHtmlMail(String to, String subject, String content) {
        sendHtmlMail(to, subject, content, null);

    }

    /**
     * html邮件
     *
     * @param to       接收人
     * @param subject  主题
     * @param content  内容
     * @param fileList 附件
     */
    public void sendHtmlMail(String to, String subject, String content, List<File> fileList) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(to);
            //设置自定义发件人昵称
            String nick="";
            try {
                nick=javax.mail.internet.MimeUtility.encodeText(nickname);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            mimeMessageHelper.setFrom(new InternetAddress(nick+" <"+username+">"));
            mimeMessageHelper.setText(content, true);
            mimeMessageHelper.setSubject(subject);
            if (fileList != null && fileList.size() > 0) {
                fileList.forEach(file -> {
                    try {
                        mimeMessageHelper.addAttachment(file.getName(), file);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                });
            }
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取模板内容
     *
     * @param data
     * @param templatePath
     * @return
     */
    private String getTemplateHtml(Map<String, Object> data, String templatePath) {
        Context context = new Context();
        context.setVariables(data);
        String emailText = templateEngine.process(templatePath, context);
        return emailText;
    }
}
