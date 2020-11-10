package com.mychum1.explorer.controller;

import com.mychum1.explorer.common.CommonCode;
import com.mychum1.explorer.common.JwtProvider;
//import com.mychum1.explorer.domain.Member;
import com.mychum1.explorer.domain.KaKaoDocuments;
import com.mychum1.explorer.domain.Member2;
import com.mychum1.explorer.domain.Response;
import com.mychum1.explorer.exception.LoginException;
import com.mychum1.explorer.repository.UserRepository;
//import com.mychum1.explorer.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.net.http.HttpHeaders;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@Controller
//@RequestMapping("/")
public class LoginController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        String tempPassword = passwordEncoder.encode("password");
        System.out.println("tempPassword : " + tempPassword);
        System.out.println(Base64.getEncoder().encodeToString(tempPassword.getBytes(StandardCharsets.UTF_8)));
        userRepository.save(new Member2("client", tempPassword));
    }

    @GetMapping(value="/test")
    public ResponseEntity<Response> test(@RequestHeader(value = "Authorization", required = false) String head) throws Exception {

        System.out.println("유효 테스트");
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, "good"), HttpStatus.OK);
    }

    @PostMapping(value="/login")
    public ResponseEntity<Response> login(@RequestHeader(value = "Authorization", required = false) String head) throws Exception {
        System.out.println("call login");
        System.out.println(head);


        String encodedUserInfo = head.replace("Bearer ", "");
        String[] encodedUserInfoArray = encodedUserInfo.split(":");
        Base64.Decoder decoder = Base64.getDecoder();
        String userName = new String(decoder.decode(encodedUserInfoArray[0].getBytes(StandardCharsets.UTF_8)));
        System.out.println("username : " + userName);
        String password = new String(decoder.decode(encodedUserInfoArray[1].getBytes(StandardCharsets.UTF_8)));

        System.out.println("password : " + password);
        // 임시로 유저 정보 인풋
        Member2 member = userRepository.findById(userName).get();
        System.out.println(member.getPassword() + " , " + password);
//        if(!passwordEncoder.matches(member.getPassword(), password)) {
//            throw new Exception("Not Password");
//        }

        if(!member.getPassword().equals(password)) {
            return new ResponseEntity<>(new Response<>(200, "OK", null), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, jwtProvider.createToken(userName)), HttpStatus.OK);



//        Member member;
//        try {
//            member = userDetailsService.loadUserByUsername(user.get("username"));
//        }catch(Exception e){
//            throw new LoginException(500, CommonCode.LogIn_ERROR, e);
//        }
//
//        if(member==null) {
//            throw new LoginException(500, CommonCode.No_USER, null);
//        }
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

        //return jwtProvider.createToken(member.getUsername(), member.getRoles());

    }

}
