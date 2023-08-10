package com.aa.resume.controllers;

import com.aa.resume.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
public class CaptchaController {

    @Autowired
    private CaptchaUtil captchaUtil;

    @GetMapping(value = "/captcha", produces = MediaType.IMAGE_PNG_VALUE)
    public String getCaptchaImage() throws IOException {
        BufferedImage captchaImage = captchaUtil.generateCaptchaImage();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(captchaImage, "png", baos);
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    @PostMapping(value = "/captcha-validation")
    public boolean validateCaptcha(@RequestBody String captchaAnswer) throws UnsupportedEncodingException {
        return captchaUtil.validateCaptcha(captchaAnswer.substring(0, captchaAnswer.length() - 1));
    }
}
