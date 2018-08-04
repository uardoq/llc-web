package com.llc.conscontweb.controller;

import com.llc.conscontweb.helpers.CCWHelpers;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {

    private TestimonialsService testimonialsService;
    private ContactService contactService;
    private SendContactMailService sendContactMailService;
    private CCWHelpers helpers;

    /* Constructor Dependency Injection */
    @Autowired
    public HomeController(TestimonialsService testimonialsService, ContactService contactService, SendContactMailService sendContactMailService, CCWHelpers helpers) {
        this.testimonialsService = testimonialsService;
        this.contactService = contactService;
        this.sendContactMailService = sendContactMailService;
        this.helpers = helpers;
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
            @Valid @ModelAttribute ContactForm contactForm,
            @RequestParam(value = "files[]", required = false) MultipartFile multipartFile,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // client input does not pass validation, set error 422 UNPROCESSABLE_ENTITY
            System.out.println("From submitContactForm(): 422, " + contactForm.toString());
            return new ResponseEntity<>(helpers.getErrorsInASaneFormat(bindingResult), HttpStatus.UNPROCESSABLE_ENTITY);
        } else {
            // validation passes, call ContactService
            System.out.println("From submitContactForm(): processing contact form");
            System.out.println("From submitContactForm(): 200, " + contactForm.toString());
            contactService.processContactForm(contactForm);

            if (multipartFile == null) {
                // no file uploaded
                sendContactMailService.notifyUs(contactForm);
            } else {
                try {
                    // get image file
                    String fileExtension = helpers.readTypeFromSignature(multipartFile.getBytes());
                    if (fileExtension == null) {
                        return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
                    } else {
                        String filename = multipartFile.getOriginalFilename();
                        // if file does not have a name, fallback to epoch time for the name
                        filename = (filename == null || filename.equals("")) ? String.valueOf(System.currentTimeMillis()) : filename;
                        File file = new File(filename);
                        BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());
                        ImageIO.write(bufferedImage, fileExtension, file);

                        // send email
                        sendContactMailService.notifyUs(new ContactFormWithAttachments(contactForm, file));

                        // delete file
                        file.delete();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // client expects json, so pass in an empty object
            return new ResponseEntity<>("{}", HttpStatus.OK);
        }
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
