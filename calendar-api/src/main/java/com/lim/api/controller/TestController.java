package com.lim.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JavaMailSender javaMailSender;

    @GetMapping("/api/mail")
    public void sendTestMail() {
        final MimeMessagePreparator preparator = (MimeMessage message) -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo("imdaegeun93@gmail.com");
            helper.setSubject("제목!!!");
            helper.setText("여기는 테스트 내용입니다. ^^");
        };
        javaMailSender.send(preparator);
    }
}
