package com.tcn.meetandnote.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String email, String subject, String body) {
        new Thread(() -> {
            try {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
                helper.setFrom("thanhnhahuynh1509@gmail.com");
                helper.setTo(email);
                helper.setSubject(subject);
                helper.setText(body, true);
                javaMailSender.send(message);
                System.out.println("Send mail successfully!");
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

}
