package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.ContactForm;

import java.io.File;
import java.util.List;

/**
 * composite of a ContactForm and any attachments
 * this class exists so we can keep the ContactForm entity data separated from the attachment
 * which does not get saved in the db, but is used by the SendMailService.
 */
public class ContactFormWithAttachments {

    private final ContactForm contactForm;
    private final List<File> files;

    ContactFormWithAttachments(ContactForm contactForm, List<File> files) {
        this.contactForm = contactForm;
        this.files = files;
    }

    public ContactForm getContactForm() {
        return contactForm;
    }

    public List<File> getFiles() {
        return files;
    }
}
