package com.aa.resume.controllers;

import com.aa.resume.data.DTOs.FormDataDTO;
import com.aa.resume.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send-email")
    public String sendEmail(@RequestBody FormDataDTO formDataDTO) {
        if (mailService.sendEmail(formDataDTO)) {
            return "Email sent successfully";
        }
        return "Email could not be sent. Please check the Email Id provided";
    }
}
