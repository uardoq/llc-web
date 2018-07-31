package com.llc.conscontweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GalleryController {

    @GetMapping(path = "/gallery")
    public String showGalleryPage(Model model) {
        // load images temporarily
//        model.addAttribute("images", images);

        return "gallery";
    }

}
