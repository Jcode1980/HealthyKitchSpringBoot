package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.stereotype.Service;
import freemarker.template.Configuration;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;

import org.springframework.mail.javamail.MimeMessageHelper;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.nio.charset.StandardCharsets;


@Service
public class EmailServiceImpl implements EmailService {

    public JavaMailSender emailSender;

    @Autowired
    EmailServiceImpl(JavaMailSender mailSender){
        this.emailSender = mailSender;
    }

    @Autowired
    private Configuration freemarkerConfig;

    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.png", new ClassPathResource("images/preview-2.png"));

        Template t = freemarkerConfig.getTemplate("BasicEmailTemplate.ftl");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());

        helper.setTo(mail.getToArray());
        helper.setBcc(mail.getBccArray());
        helper.setText(html, true);
        helper.setSubject(mail.getSubject());
        helper.setFrom(mail.getFrom());

        emailSender.send(message);
    }


}
