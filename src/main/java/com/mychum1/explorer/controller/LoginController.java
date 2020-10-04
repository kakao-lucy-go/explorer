package com.mychum1.explorer.controller;

import com.mychum1.explorer.common.CommonCode;
import com.mychum1.explorer.common.JwtProvider;
import com.mychum1.explorer.domain.Member;
import com.mychum1.explorer.exception.LoginException;
import com.mychum1.explorer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    MemberService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;


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
    public String getLogin() {
        return "login";
    }

    @PostMapping(value="/login2")
    public String login(@RequestBody Map<String, String> user) throws Exception {

        Member member;
        try {
            member = userDetailsService.loadUserByUsername(user.get("username"));
        }catch(Exception e){
            throw new LoginException(500, CommonCode.LogIn_ERROR, e);
        }

        if(member==null) {
            throw new LoginException(500, CommonCode.No_USER, null);
        }
        //패스워드 확인
//        if(!passwordEncoder.matches(member.getPassword(), "{bcrypt}"+user.get("password"))) {
//
//            System.out.println("No password");
//            throw new Exception("Not Password");
//        }

//        List<String> roldList = new ArrayList<>();
//        if(member.getUsername().equals("client")) {
//            roldList.add("ADMIN");
//        }else {
//            roldList.add("USER");
//        }

        return jwtProvider.createToken(member.getUsername(), member.getRoles());

    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value="/rank")
    public String rank() {
        return "ranking";
    }
}
