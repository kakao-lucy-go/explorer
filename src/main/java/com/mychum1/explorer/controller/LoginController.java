package com.mychum1.explorer.controller;

import com.mychum1.explorer.common.CommonCode;
import com.mychum1.explorer.common.JwtProvider;
//import com.mychum1.explorer.domain.Member;
import com.mychum1.explorer.domain.*;
import com.mychum1.explorer.exception.LoginException;
import com.mychum1.explorer.repository.BlackListRepository;
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
//import java.net.http.HttpHeaders;
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

    @Autowired
    BlackListRepository blackListRepository;

    @PostConstruct
    public void initialize() {
        String tempPassword = passwordEncoder.encode("password");
        System.out.println("tempPassword : " + tempPassword);
        System.out.println(Base64.getEncoder().encodeToString("client".getBytes(StandardCharsets.UTF_8)));
        System.out.println(Base64.getEncoder().encodeToString(tempPassword.getBytes(StandardCharsets.UTF_8)));
        userRepository.save(new Member2("client", tempPassword));
    }

    @GetMapping(value="/test")
    public ResponseEntity<Response> test(@RequestHeader(value = "Authorization", required = false) String head) throws Exception {

        System.out.println("유효 테스트");
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, "good"), HttpStatus.OK);
    }

    @PostMapping(value="/signup")
    public ResponseEntity<Response> signup(@RequestHeader(value = "clientid", required = true) String clientid,
                                           @RequestHeader(value = "password", required = true) String password) throws Exception {
        String tempPassword = passwordEncoder.encode(password);
        userRepository.save(new Member2(clientid, tempPassword ));

        //임시
        String result = Base64.getEncoder().encodeToString(clientid.getBytes(StandardCharsets.UTF_8))+":"+Base64.getEncoder().encodeToString(tempPassword.getBytes(StandardCharsets.UTF_8));
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, result), HttpStatus.OK);
    }

    @PostMapping(value="/logout")
    public ResponseEntity<Response> logout(@RequestHeader(value = "Authorization", required = false) String head) throws Exception {

        //리프레시 토큰 삭제
        String userName = jwtProvider.getUserNameFromToken(head);
        Member2 member = userRepository.findById(userName).get();
        member.setRefreshToken(null);
        userRepository.save(member);
        //블랙리스트에 토큰 추가. 추후 필터에서 만료된 토큰인지 확인해야한다.
        blackListRepository.save(new BlackList(head.replace("Bearer ","")));
        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, "success"), HttpStatus.OK);


    }

    @PostMapping(value="/refresh")
    public ResponseEntity<Response> refresh(@RequestHeader(value = "Authorization", required = false) String head) throws Exception {

        //TODO filter 에서 duration이 걸리면 프론트에서는 로그아웃 프로세스를 진행해야한다.
        String userName = jwtProvider.getUserNameFromToken(head);
        Member2 member2 = userRepository.findById(userName).get();
        //refresh token 일치! -> 예전 리프레시 토큰으로 현재걸 리프레시할 수 없도록 하기 위해
        if(head.replace("Bearer ","").equals(member2.getRefreshToken())) {

            String refreshToken = jwtProvider.createRefreshToken(userName);
            member2.setRefreshToken(refreshToken);
            userRepository.save(member2);
            blackListRepository.save(new BlackList(head.replace("Bearer ","")));

            return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, new TokenResponse(jwtProvider.createToken(userName), refreshToken) ), HttpStatus.OK);

        }else {
            return new ResponseEntity<>(new Response<>(HttpStatus.BAD_REQUEST.value(), CommonCode.INTERNAL_SERVER_ERROR, null ), HttpStatus.BAD_REQUEST);

        }
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

        if(!member.getPassword().equals(password)) {
            return new ResponseEntity<>(new Response<>(200, "OK", null), HttpStatus.BAD_REQUEST);
        }

        String refreshToken = jwtProvider.createRefreshToken(userName);
        member.setRefreshToken(refreshToken);
        userRepository.save(member);

        return new ResponseEntity<>(new Response<>(HttpStatus.OK.value(), CommonCode.SUCCESS_MSG, new TokenResponse(jwtProvider.createToken(userName), refreshToken) ), HttpStatus.OK);



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
