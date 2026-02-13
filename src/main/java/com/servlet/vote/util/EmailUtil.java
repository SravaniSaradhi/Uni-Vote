package com.servlet.vote.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class EmailUtil {

    public static void sendOTP(String toEmail, String otp) {

        final String fromEmail = "univoteproject@gmail.com";
        final String password = "vdediyvuibafdxys"; // Gmail App Password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
            new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("Uni-Vote Email Verification");
            msg.setText("Your OTP for Uni-Vote registration is: " + otp);

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
