package com.example.notifier.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailNotificationService {

    @Value("${SMTP_SERVER}")
    private String smtpServer;

    @Value("${SMTP_PORT}")
    private int smtpPort;

    @Value("${EMAIL_ADDRESS}")
    private String emailAddress;

    @Value("${EMAIL_PASSWORD}")
    private String emailPassword;

        public void sendEmail(String to, String subject, String text) {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", smtpPort);

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(emailAddress, emailPassword);
                }
            });

            try {
                System.out.println("[EmailNotificationService] Preparing to send email");
                System.out.println("[EmailNotificationService] SMTP host: " + smtpServer);
                System.out.println("[EmailNotificationService] SMTP port: " + smtpPort);
                System.out.println("[EmailNotificationService] SMTP auth: " + "true");
                System.out.println("[EmailNotificationService] SMTP starttls: " + "true");
                System.out.println("[EmailNotificationService] SMTP username: " + emailAddress);
                System.out.println("[EmailNotificationService] SMTP from: " + emailAddress);
                System.out.println("[EmailNotificationService] To: " + to);
                System.out.println("[EmailNotificationService] Subject: " + subject);
                System.out.println("[EmailNotificationService] Text: " + text);

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(emailAddress));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
                message.setSubject(subject);
                message.setText(text);

                System.out.println("[EmailNotificationService] Sending email...");
                Transport.send(message);
                System.out.println("[EmailNotificationService] Email sent successfully");
            } catch (MessagingException e) {
                System.out.println("[EmailNotificationService] Failed to send email: " + e.getMessage());
                e.printStackTrace();
                throw new RuntimeException("Failed to send email", e);
            }
    }
}