package com.llc.conscontweb.service;

import com.llc.conscontweb.model.ContactForm;
import com.llc.conscontweb.model.ContactFormWithAttachments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Locale;

@Service
public class SendContactMailServiceImpl implements SendContactMailService {

    /* Bean by starter-mail dependency */
    private JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String email;

    @Autowired
    public SendContactMailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    private MimeMessageHelper buildBaseMessage(ContactForm contactForm, boolean hasAttachment) throws MessagingException {

        // set variables for template
        final Context ctx = new Context(Locale.getDefault());
        ctx.setVariable("contactForm", contactForm);
        String content = templateEngine.process("email_templates/email-simple", ctx);

        // send us an email with the clients message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        // set to true because we will send a multi part message
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, hasAttachment, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject("[QUESTION] from: " + contactForm.getName());
        mimeMessageHelper.setText(content, true);
        return mimeMessageHelper;
    }

    @Override
    public void notifyUs(ContactForm contactForm) {
        try {
            MimeMessageHelper mimeMessageHelper = buildBaseMessage(contactForm, false);

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
            MimeMessageHelper mimeMessageHelper = buildBaseMessage(contactForm, true);

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
