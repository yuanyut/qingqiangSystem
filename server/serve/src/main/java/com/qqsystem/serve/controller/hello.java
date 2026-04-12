package com.qqsystem.serve.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hello {
    @GetMapping
    public String hello(){
        return "hell";
    }
}
