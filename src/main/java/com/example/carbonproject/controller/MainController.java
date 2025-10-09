package com.example.carbonproject.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@ResponseBody//api서버 활용 -> 객체 혹은 string data 응답
public class MainController {

    @GetMapping("/")
    public String mainP(){

        String username= SecurityContextHolder.getContext().getAuthentication().getName();

        return "Main Controller: "+username;
    }

}
