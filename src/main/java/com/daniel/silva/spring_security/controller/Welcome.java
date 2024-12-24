package com.daniel.silva.spring_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {

    @GetMapping("/secure")
    public String text(){
        return "WELCOME TO SPRING APPLICATION WITH SECURITY @#@";
    }

    @GetMapping("/unsecure")
    public String text1(){
        return "WELCOME TO ROTE WITH OUT SECURITY @$@";
    }
}
