package com.nutritionalStylist.healthyKitch.service;

import com.nutritionalStylist.healthyKitch.model.Mail;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailService {

    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException, TemplateException;
}
