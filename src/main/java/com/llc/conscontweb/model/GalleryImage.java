package com.llc.conscontweb.model;

import javax.persistence.*;
import java.net.URI;

@Entity
@Table(name = "gallery_image")
public class GalleryImage {

    private long Id;
    private URI path;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    @Column
    public URI getPath() {
        return path;
    }

    public void setPath(URI path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "GalleryImage{" +
                "Id=" + Id +
                ", path=" + path +
                '}';
    }
}
