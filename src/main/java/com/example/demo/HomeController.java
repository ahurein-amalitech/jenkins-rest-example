package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController {

    @GetMapping()
    public String health(){
        return "It works";
    }

    @GetMapping("hello")
    public String hello(){
        return "Hello ahurein";
    }

    @GetMapping("age")
    public int getAge(){
        return 8;
    }
}