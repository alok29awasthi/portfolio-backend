package com.aa.resume.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;
import cn.apiclub.captcha.gimpy.DropShadowGimpyRenderer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.Objects;

@Service
public class CaptchaUtil {

    @Autowired
    private CacheManager cacheManager;

    public BufferedImage generateCaptchaImage() {
        Captcha captcha = new Captcha.Builder(150, 50)
                .addText(new DefaultWordRenderer(Collections.singletonList(Color.BLACK), Collections.singletonList(new Font("Arial", Font.BOLD, 40))))
                .addBackground(new FlatColorBackgroundProducer(Color.WHITE))
                .addNoise(new CurvedLineNoiseProducer(Color.GRAY, 2))
                .gimp(new DropShadowGimpyRenderer())
                .build();

        saveCaptchaAnswer(captcha);
        return captcha.getImage();
    }

    public void saveCaptchaAnswer(Captcha captcha) {
        Cache cache = cacheManager.getCache("captchaCache");
        if (Objects.nonNull(cache)) {
            cache.put("captchaId", captcha.getAnswer());
        }
    }

    public boolean validateCaptcha(String captchaAnswer) {
        Cache cache = cacheManager.getCache("captchaCache");
        Cache.ValueWrapper cacheValue = cache.get("captchaId");
        return captchaAnswer.equals(cacheValue.get().toString());
    }
}
