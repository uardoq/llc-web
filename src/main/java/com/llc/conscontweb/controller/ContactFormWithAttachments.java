package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.ContactForm;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * composite of a ContactForm and any attachments
 * this class exists so we can keep the ContactForm entity data separated from the attachment
 * which does not get saved in the db, but is used by the SendMailService.
 */
public class ContactFormWithAttachments {

    private final ContactForm contactForm;
    private final File file;

    public ContactFormWithAttachments(ContactForm contactForm, File file) {
        this.contactForm = contactForm;
        this.file = file;
    }

    public ContactForm getContactForm() {
        return contactForm;
    }

    public File getFile() {
        return file;
    }
}
