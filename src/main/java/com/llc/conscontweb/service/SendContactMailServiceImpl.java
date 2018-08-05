package com.llc.conscontweb.service;

import com.llc.conscontweb.controller.ContactFormWithAttachments;
import com.llc.conscontweb.model.ContactForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
public class SendContactMailServiceImpl implements SendContactMailService {

    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    public SendContactMailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private MimeMessageHelper buildBaseMessage(String to, String subject, String message, boolean hasAttachment) throws MessagingException {
        // send us an email with the clients message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // set to true because we will send a multi part message
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, hasAttachment);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(message);
        return mimeMessageHelper;
    }

    @Override
    public void notifyUs(ContactForm contactForm) {

        try {
            MimeMessageHelper mimeMessageHelper = buildBaseMessage(email,
                    "[QUESTION] from : " + contactForm.getName(),
                    "Name: " + contactForm.getName() + "\n" +
                            "Email: " + contactForm.getEmail() + "\n" +
                            "Message: " + contactForm.getMessage(),
                    false);

            // send message
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyUs(ContactFormWithAttachments contactFormWithAttachments) {
        ContactForm contactForm = contactFormWithAttachments.getContactForm();

        try {
            MimeMessageHelper mimeMessageHelper = buildBaseMessage(email,
                    "[QUESTION] from : " + contactForm.getName(),
                    "Name: " + contactForm.getName() + "\n" +
                            "Email: " + contactForm.getEmail() + "\n" +
                            "Message: " + contactForm.getMessage(),
                    true);

            // add attachment
            // TODO: only add attachment if contactForm.getFiles() returns images

            // add all image files
            List<File> files = contactFormWithAttachments.getFiles();
            for (File file : files) {
                FileSystemResource fileSystemResource = new FileSystemResource(file);
                mimeMessageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);
            }

            // send
            javaMailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
