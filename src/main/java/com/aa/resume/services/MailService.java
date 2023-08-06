package com.aa.resume.services;

import com.aa.resume.data.DTOs.FormDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.aa.resume.constants.EmailConstants.*;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    public boolean sendEmail(FormDataDTO formDataDTO) {
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
            helper.setTo(formDataDTO.getEmail());
            helper.setSubject(SUBJECT);

            Context context = new Context();
            context.setVariable(NAME, formDataDTO.getFirstName());
            context.setVariable(MESSAGE, formDataDTO.getMessage());

            String processedTemplate = templateEngine.process(EMAIL_TEMPLATE, context);
            helper.setText(processedTemplate, true);

            javaMailSender.send(mailMessage);
            return true;
        } catch (MailException | MessagingException e) {
            return false;
        }
    }
}
