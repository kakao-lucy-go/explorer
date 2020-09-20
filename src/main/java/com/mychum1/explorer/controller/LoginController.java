package com.mychum1.explorer.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping(value="/denied")
    public String deny() {
        return "denied";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value="/")
    public String index() {
        return "index";
    }

    @PostMapping(value="/login")
    public String login() {
        return "login";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value="/rank")
    public String rank() {
        return "ranking";
    }
}
