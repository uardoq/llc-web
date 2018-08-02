package com.llc.conscontweb.service;

import com.llc.conscontweb.model.ContactForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class SendContactMailServiceImpl implements SendContactMailService {

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String email;

    public SendContactMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void notifyUs(ContactForm contactForm) {

        // send us an email with the clients message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            // set to true because we will send a multi part message
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setSubject("[QUESTION] from : " + contactForm.getName());
            mimeMessageHelper.setText("Name: " + contactForm.getName() + "\n" +
                    "Email: " + contactForm.getEmail() + "\n" +
                    "Message: " + contactForm.getMessage());

            // add attachment
            // TODO: only add attachment if contactForm.getFiles() returns images
//            FileSystemResource fileSystemResource = new FileSystemResource(
//                    new File("/home/edqu/Projects/conscontweb/src/main/resources/static/images/image11.jpeg"));
//            mimeMessageHelper.addAttachment("cool_image.jpg", fileSystemResource);

            // send message
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
