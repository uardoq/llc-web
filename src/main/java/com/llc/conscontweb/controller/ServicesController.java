package com.llc.conscontweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServicesController {

    @GetMapping(path = {"/services"})
    public String showServicesPage(){

        return "services";
    }

}
