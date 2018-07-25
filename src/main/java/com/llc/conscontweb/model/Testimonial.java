package com.llc.conscontweb.model;

import javax.persistence.*;

@Entity
public class Testimonial {

    private Long id;
    /* max rating is contained in properties file */
    private Float rating;
    private String poster;
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Testimonial{" +
                "rating=" + rating +
                ", poster='" + poster + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
