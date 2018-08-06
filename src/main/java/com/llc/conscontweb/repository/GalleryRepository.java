package com.llc.conscontweb.repository;

import com.llc.conscontweb.model.GalleryImage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GalleryRepository extends PagingAndSortingRepository<GalleryImage, Long> {

}
