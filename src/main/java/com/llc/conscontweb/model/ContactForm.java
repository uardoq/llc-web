package com.llc.conscontweb.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "contact_form")
public class ContactForm {

    /* All columns in the database are named after the field names by default */

    private long id;
    private String name;
    private String email;
    private String message;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull(message = "Contact name is required")
    @Size(min = 2, message = "Contact Name is too short")
    @Pattern(regexp = "^[a-zA-Z]{2,}(\\s+[a-zA-Z]+)*$", message = "Please enter a valid contact name")
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Email is required")
    @Size(min = 3, message = "Email is too short")
    @Pattern(regexp = "^.+@.+$", message = "Invalid email format")
    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull(message = "Please write an inquiry")
    @Size(min = 80, message = "Inquiry is too short")
    @Column(nullable = false, columnDefinition = "LONGTEXT")
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
