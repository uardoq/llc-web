package com.llc.conscontweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class GalleryController {

    // need a service that gives us images


    @GetMapping(path = "/gallery")
    public String showGalleryPage(Model model) {

//        List<String> imageUrls = new ArrayList<String>();
//        imageUrls.add("/home/edqu/Projects/conscontweb/src/main/resources/static/images/image12.jpeg");

        // load images temporarily
//        model.addAttribute("images", imageUrls);
        ArrayList<String> images = new ArrayList<>();
//        images.add("/home/edqu/Projects/conscontweb/src/main/resources/static/images/image12.jpeg");
        images.add("/images/image1.jpg");
        images.add("/images/image2.jpg");
        images.add("/images/image3.jpg");
        images.add("/images/image4.jpg");
        images.add("/images/image5.jpg");
        images.add("/images/image6.jpg");
        images.add("/images/image7.jpg");
        images.add("/images/image8.jpg");
        images.add("/images/image9.jpg");
        images.add("/images/image10.jpg");
        images.add("/images/image11.jpeg");
        images.add("/images/image12.jpeg");
        model.addAttribute("images", images);

        return "gallery";
    }

}
