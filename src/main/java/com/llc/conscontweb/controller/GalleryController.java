package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.GalleryImage;
import com.llc.conscontweb.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GalleryController {

    @Value("${site.pageSize}")
    private int pageSize;
    private GalleryService galleryService;

    @Autowired
    public GalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping(path = "/gallery")
    public String showGalleryPage(@RequestParam(value = "page", defaultValue = "0") int page, Model model) {

        List<GalleryImage> galleryURIs = galleryService.getGalleryURIs(page, pageSize).getContent();
        model.addAttribute("images", galleryURIs);
        model.addAttribute("pageNumber", page);

        // load images temporarily
        return "gallery";
    }

}
