package com.llc.conscontweb.service;

import com.llc.conscontweb.model.ContactForm;
import com.llc.conscontweb.repository.ContactFormsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private ContactFormsRepository contactFormsRepository;

    @Autowired
    public ContactServiceImpl(ContactFormsRepository contactFormsRepository) {
        this.contactFormsRepository = contactFormsRepository;
    }

    @Override
    public void processContactForm(ContactForm contactEntry) {
        // save contact to ContactFormsRepository repository
        contactFormsRepository.save(contactEntry);
        List<ContactForm> all = contactFormsRepository.findAll();

        // TODO: send email

    }
}
