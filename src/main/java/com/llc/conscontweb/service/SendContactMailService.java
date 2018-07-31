package com.llc.conscontweb.service;

import com.llc.conscontweb.model.ContactForm;

public interface SendContactMailService {
    void notifyUs(ContactForm contactForm);
}
