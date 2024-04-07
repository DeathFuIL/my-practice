package ru.kpfu.itis.controllers;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/")
public class MainPageController {

    @GetMapping
    public String index(Principal principal) {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) principal;
        System.out.println(token);
        return "index";
    }

    @GetMapping("/secured")
    public String secured(Principal principal) {
        System.out.println(principal.getName());
        return "index";
    }
}
