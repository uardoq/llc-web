package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.ContactForm;
import org.springframework.web.multipart.MultipartFile;

/**
 * composite of a ContactForm and any attachments
 * this class exists so we can keep the ContactForm entity data separated from the attachment
 * which does not get saved in the db, but is used by the SendMailService.
 */
public class ContactFormWithAttachments {

    private final ContactForm contactForm;
    private final MultipartFile multipartFile;

    public ContactFormWithAttachments(ContactForm contactForm, MultipartFile multipartFile) {
        this.contactForm = contactForm;
        this.multipartFile = multipartFile;
    }

    public ContactForm getContactForm() {
        return contactForm;
    }

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }
}
