package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.ContactForm;
import com.llc.conscontweb.model.Testimonial;
import com.llc.conscontweb.service.ContactService;
import com.llc.conscontweb.service.SendContactMailService;
import com.llc.conscontweb.service.TestimonialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {

    private TestimonialsService testimonialsService;
    private ContactService contactService;
    private SendContactMailService sendContactMailService;

    /* Constructor Dependency Injection */
    @Autowired
    public HomeController(TestimonialsService testimonialsService, ContactService contactService, SendContactMailService sendContactMailService) {
        this.testimonialsService = testimonialsService;
        this.contactService = contactService;
        this.sendContactMailService = sendContactMailService;
    }

    @GetMapping(path = {"/"})
    public String showHomepage(Model model) {
        // get testimonials from repo
        List<Testimonial> testimonials = testimonialsService.getAllTestimonials();
        if (!testimonials.isEmpty()) {
            model.addAttribute("testimonials", testimonials);
            // this ContactForm object gets bound to the form element in the html
            model.addAttribute("contactForm", new ContactForm());
        }
        // render view
        return "homepage";
    }

    /**
     * Handle client request made when they submit a contact form.
     *
     * @return if input is valid return 200, else return 422 and FieldErrors
     */
    @ResponseBody
    @PostMapping(path = {"/submitContactForm"}, consumes = {"multipart/form-data"}, produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> submitContactForm(
            /*@Valid @RequestBody ContactForm contactForm,*/
            @RequestParam("files[]") MultipartFile multipartFile
            /*BindingResult bindingResult*/) {

        try {
            String ctype = multipartFile.getContentType();
            String name = multipartFile.getOriginalFilename();
            // currently it saves the image to our root directory,
            // TODO: save to a filesystem instead
            BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
            // TODO: parse type out of ctype
            ImageIO.write(bufferedImage, "jpg", new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }

/*
        if (bindingResult.hasErrors()) {
            *//* client input does not pass validation, set error 422 UNPROCESSABLE_ENTITY *//*
            System.out.println("From submitContactForm(): 422, " + contactForm.toString());
            return new ResponseEntity<>(getErrorsInASaneFormat(bindingResult), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            // validation passes, call ContactService
            System.out.println("From submitContactForm(): processing contact form");
            System.out.println("From submitContactForm(): 200, " + contactForm.toString());
            contactService.processContactForm(contactForm);
            sendContactMailService.notifyUs(contactForm);
            // client expects json, so pass in an empty object
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
        */

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    /**
     * Returns a map of the fields as keys and error description as values.
     */
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
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        // trim to null if string is only spaces
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
