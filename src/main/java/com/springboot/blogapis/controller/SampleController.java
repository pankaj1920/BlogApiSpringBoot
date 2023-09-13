package com.springboot.blogapis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class SampleController {

    @GetMapping("testing")
    public String testing(){
        return "Testing Success";
    }
}
