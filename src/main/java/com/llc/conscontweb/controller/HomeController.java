package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.ContactForm;
import com.llc.conscontweb.model.Testimonial;
import com.llc.conscontweb.repository.TestimonialsRepository;
import com.llc.conscontweb.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private TestimonialsRepository testimonialsRepository;
    private ContactService contactService;

    @Value("${max-rating}")
    private Float maxRating;

    /* Constructor Dependency Injection */
    @Autowired
    public HomeController(TestimonialsRepository testimonialsRepository, ContactService contactService) {
        this.testimonialsRepository = testimonialsRepository;
        this.contactService = contactService;
    }

    @GetMapping(path = {"/"})
    public String showHomepage(Model model) {
        // get testimonials from repo
        List<Testimonial> testimonials = testimonialsRepository.findAll();
        if (!testimonials.isEmpty()) {
            model.addAttribute("testimonials", testimonials);
            model.addAttribute("maxVal", maxRating);
            // this ContactForm object gets bound to the contact form
            model.addAttribute("contactForm", new ContactForm());
        }
        // render view
        return "homepage";
    }


    /**
     * Handle client request made when they submit a contact form.
     *
     * @param contactForm
     * @param bindingResult
     * @return if input is valid return 200, else return 422 and FieldErrors
     */
    @ResponseBody
    @PostMapping(path = {"/submitContactForm"}, produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> submitContactForm(
            @Valid @RequestBody ContactForm contactForm,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            /* client input does not pass validation, set error 422 UNPROCESSABLE_ENTITY */
            System.out.println("From submitContactForm(): 422," + contactForm.toString());
            return new ResponseEntity<>(getErrorsInASaneFormat(bindingResult), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            // validation passes, call ContactService
            System.out.println("From submitContactForm(): processing contact form");
            System.out.println("From submitContactForm(): 200, " + contactForm.toString());
            contactService.processContactForm(contactForm);
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }

    }

    private Map<String, String> getErrorsInASaneFormat(final BindingResult result) {
        return new HashMap<String, String>() {{
            for (FieldError error : result.getFieldErrors()) {
                put(error.getField(), error.getDefaultMessage());
            }
        }};
    }

    /**
     * Register this as a preprocessor for string bindings.
     * Invalidates string inputs consisting of only spaces
     *
     * @param webDataBinder
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        // trim to null if string is only spaces
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);

    }

}
