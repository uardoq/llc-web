package com.llc.conscontweb.service;

import com.llc.conscontweb.model.GalleryImage;
import org.springframework.data.domain.Page;

public interface GalleryService {

    Page<GalleryImage> getGalleryURIs(int page, int pageSize);

}
