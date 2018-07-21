package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.ContactForm;
import com.llc.conscontweb.model.Testimonial;
import com.llc.conscontweb.repository.TestimonialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private TestimonialsRepository testimonialsRepository;

    @Value("${max-rating}")
    private Float maxRating;

    /* Constructor dependency Injection */
    @Autowired
    public HomeController(TestimonialsRepository testimonialsRepository) {
        this.testimonialsRepository = testimonialsRepository;
    }

    @GetMapping(path = {"/"})
    public String showHomepage(Model model) {
        // get testimonials from repo
        List<Testimonial> testimonials = testimonialsRepository.findAll();
        if (testimonials != null) {
            /* add to model */
            model.addAttribute("testimonials", testimonials);
            model.addAttribute("maxVal", maxRating);
            // this ContactForm object gets bound to the contact form
            model.addAttribute("contactForm", new ContactForm());
        }
        // render view
        return "homepage";
    }

    @PostMapping(path = {"/submitContactForm"})
    public String submitContactForm(@Valid @ModelAttribute ContactForm contactForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("has errors -----------");
        }

        System.out.print(contactForm.toString());
        return "redirect:/";
    }

    /**
     * register this as a preprocessor for string bindings
     **/
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        // trim to null if string is only spaces
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

    }

}
