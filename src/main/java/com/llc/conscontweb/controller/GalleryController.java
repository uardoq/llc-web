package com.llc.conscontweb.controller;

import com.llc.conscontweb.model.GalleryImage;
import com.llc.conscontweb.service.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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

        // guard for non existent pages
        page = page < 0 ? 0 : page;

        Page<GalleryImage> galleryURIs = galleryService.getGalleryURIs(page, pageSize);
        List<GalleryImage> images = galleryURIs.getContent();
        int totalPages = galleryURIs.getTotalPages();

        page = page > totalPages ? totalPages : page;

        model.addAttribute("images", images);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        // load images temporarily
        return "gallery";
    }

}
