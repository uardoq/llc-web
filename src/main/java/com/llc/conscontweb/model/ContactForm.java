package com.llc.conscontweb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = "Contact name is required")
    @Size(min = 2, message = "Contact Name is too short")
    @Pattern(regexp = "^[a-zA-Z]{2,}(\\s+[a-zA-Z]+)*$", message = "Please enter a valid contact name")
    private String name;

    @NotNull(message = "Email is required")
    @Size(min = 3, message = "Email is too short")
    @Pattern(regexp = "^.+@.+$", message = "Invalid email format")
    private String email;

    @NotNull(message = "Please write an inquiry")
    @Size(min = 80, message = "Inquiry is too short")
    private String message;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ContactForm{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
