package com.llc.conscontweb.service;

import com.llc.conscontweb.model.Testimonial;
import com.llc.conscontweb.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestimonialsServiceImpl implements TestimonialsService {

    private TestimonialsRepository testimonialsRepository;

    @Autowired
    public TestimonialsServiceImpl(TestimonialsRepository testimonialsRepository) {
        this.testimonialsRepository = testimonialsRepository;
    }

    @Override
    public List<Testimonial> getAllTestimonials() {
        return testimonialsRepository.findAll();
    }
}
