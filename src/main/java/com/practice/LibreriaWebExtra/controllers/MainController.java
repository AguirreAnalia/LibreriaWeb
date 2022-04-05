package com.practice.LibreriaWebExtra.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class MainController {
    
    @GetMapping()
    public String index(){
        
        return "index.html";
    }
    
}
