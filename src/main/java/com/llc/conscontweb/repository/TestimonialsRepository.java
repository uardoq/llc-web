package com.llc.conscontweb.repository;

import com.llc.conscontweb.model.Testimonial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestimonialsRepository extends JpaRepository<Testimonial, Long> {
    /* provides JPA methods and queries */

}
