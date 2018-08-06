package com.llc.conscontweb.service;

import com.llc.conscontweb.model.GalleryImage;
import com.llc.conscontweb.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class GalleryServiceImpl implements GalleryService {

    private GalleryRepository galleryRepository;

    @Autowired
    public GalleryServiceImpl(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @Override
    public Page<GalleryImage> getGalleryURIs(int page, int pageSize) {
        return galleryRepository.findAll(new PageRequest(page, pageSize));
    }

}
