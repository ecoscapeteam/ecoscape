package com.java2024.ecoscape.services;

import com.java2024.ecoscape.dto.BookingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
        @Autowired

        //It is used to send emails by interacting with the JavaMail API.
        //JavaMailSender، ويُستخدم لإرسال رسائل البريد الإلكتروني من خلال
        private JavaMailSender javaMailSender; //It is used to send emails by interacting with the JavaMail API.

   // This is the method that sends an email. It takes three parameters: to (the recipient's email address),
   // subject (the email's subject), and text (the content of the email).
        public void sendEmail(String to, String subject, String text) {

   //This creates a new instance of SimpleMailMessage,
   // which is a Spring class used to represent a simple email message.
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("ecoscapeteamorg@gmail.com");  // استخدم بريد الإلكتروني هنا
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            //This sends the email message using the JavaMailSender.
            // It uses the configured SMTP settings to send the email.
            javaMailSender.send(message);
        }


    }

