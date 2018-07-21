package com.llc.conscontweb;

import com.llc.conscontweb.model.Testimonial;
import com.llc.conscontweb.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DatasourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private TestimonialsRepository testimonialsRepository;

    private DatasourceInitializer(TestimonialsRepository testimonialsRepository) {
        this.testimonialsRepository = testimonialsRepository;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        Testimonial testimonial = new Testimonial();
        testimonial.setContent("this is a test testimonial");
        testimonial.setRating(9.5f);
        testimonial.setPoster("Eduardo");

        Testimonial testimonial1 = new Testimonial();
        testimonial1.setContent("this is another test testimonial");
        testimonial1.setRating(3.5f);
        testimonial1.setPoster("Juana");

        Testimonial testimonial2 = new Testimonial();
        testimonial2.setContent("this is another test testimonial");
        testimonial2.setRating(5.5f);
        testimonial2.setPoster("content");

        testimonialsRepository.save(testimonial);
        testimonialsRepository.save(testimonial1);
        testimonialsRepository.save(testimonial2);
    }

}

