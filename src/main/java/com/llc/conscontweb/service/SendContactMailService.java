package com.llc.conscontweb.service;

import com.llc.conscontweb.model.ContactFormWithAttachments;
import com.llc.conscontweb.model.ContactForm;

public interface SendContactMailService {
    void notifyUs(ContactForm contactForm);

    void notifyUs(ContactFormWithAttachments contactForm);
}
