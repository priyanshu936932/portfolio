package com.priyanshu.portfolio.email;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    private static final String ADMIN_EMAIL = "priyanshumisra3141@gmail.com";

    private final JavaMailSender mailSender;

    public void sendContactNotification(String senderName, String senderEmail, String subject, String message) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();
            mail.setFrom(ADMIN_EMAIL);
            mail.setTo(ADMIN_EMAIL);
            mail.setReplyTo(senderEmail);
            mail.setSubject("New portfolio contact: " + (subject != null && !subject.isBlank() ? subject : "No subject"));
            mail.setText(
                    "You received a new message from your portfolio site.\n\n" +
                    "From: " + senderName + " <" + senderEmail + ">\n\n" +
                    "Message:\n" + message
            );
            mailSender.send(mail);
        } catch (Exception e) {
            logger.error("Failed to send contact notification email", e);
        }
    }
}