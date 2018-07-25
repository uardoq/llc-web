package com.llc.conscontweb.repository;

import com.llc.conscontweb.model.ContactForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactFormsRepository extends JpaRepository<ContactForm, Long> {



}
