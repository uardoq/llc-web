package com.llc.conscontweb;

import com.llc.conscontweb.model.GalleryImage;
import com.llc.conscontweb.model.Testimonial;
import com.llc.conscontweb.repository.GalleryRepository;
import com.llc.conscontweb.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class DatasourceInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final TestimonialsRepository testimonialsRepository;
    private final GalleryRepository galleryRepository;

    @Autowired
    public DatasourceInitializer(TestimonialsRepository testimonialsRepository, GalleryRepository galleryRepository) {
        this.testimonialsRepository = testimonialsRepository;
        this.galleryRepository = galleryRepository;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        // add sample testimonials
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

        // add sample images
        GalleryImage galleryImage1 = new GalleryImage();
        galleryImage1.setPath(URI.create("/images/image1.jpg"));
        GalleryImage galleryImage2 = new GalleryImage();
        galleryImage2.setPath(URI.create("/images/image2.jpg"));
        GalleryImage galleryImage3 = new GalleryImage();
        galleryImage3.setPath(URI.create("/images/image3.jpg"));
        GalleryImage galleryImage4 = new GalleryImage();
        galleryImage4.setPath(URI.create("/images/image4.jpg"));
        GalleryImage galleryImage5 = new GalleryImage();
        galleryImage5.setPath(URI.create("/images/image5.jpg"));
        GalleryImage galleryImage6 = new GalleryImage();
        galleryImage6.setPath(URI.create("/images/image6.jpg"));
        GalleryImage galleryImage7 = new GalleryImage();
        galleryImage7.setPath(URI.create("/images/image7.jpg"));
        GalleryImage galleryImage8 = new GalleryImage();
        galleryImage8.setPath(URI.create("/images/image8.jpg"));
        GalleryImage galleryImage9 = new GalleryImage();
        galleryImage9.setPath(URI.create("/images/image9.jpg"));
        GalleryImage galleryImage10 = new GalleryImage();
        galleryImage10.setPath(URI.create("/images/image10.jpg"));
        GalleryImage galleryImage11 = new GalleryImage();
        galleryImage11.setPath(URI.create("/images/image11.jpeg"));
        GalleryImage galleryImage12 = new GalleryImage();
        galleryImage12.setPath(URI.create("/images/image12.jpeg"));

        galleryRepository.save(galleryImage1);
        galleryRepository.save(galleryImage2);
        galleryRepository.save(galleryImage3);
        galleryRepository.save(galleryImage4);
        galleryRepository.save(galleryImage5);
        galleryRepository.save(galleryImage6);
        galleryRepository.save(galleryImage7);
        galleryRepository.save(galleryImage8);
        galleryRepository.save(galleryImage9);
        galleryRepository.save(galleryImage10);
        galleryRepository.save(galleryImage11);
        galleryRepository.save(galleryImage12);

    }

}

