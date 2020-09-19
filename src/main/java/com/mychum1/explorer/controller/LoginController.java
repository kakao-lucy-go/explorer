package com.mychum1.explorer.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    //TODO 잘못된 링크로 요청하고 나서 로그인을 하면 다시 그 잘못된 링크로 넘어가는데 홈으로 가야함.
    //TODO 로그아웃 페이지 맛들 것


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
