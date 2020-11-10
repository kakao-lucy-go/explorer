package com.mychum1.explorer.config;

import com.mychum1.explorer.common.JwtProvider;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Base64;

@Configuration
public class MainConfig {
    @Bean
    public Server h2TcpServer() throws SQLException {
        return Server.createTcpServer().start();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public JwtProvider jwtProvider() {return new JwtProvider();}

    @Bean
    public PasswordEncoder passwordEncoder() {

        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

//        System.out.println(Base64.getEncoder().encodeToString("client".getBytes(StandardCharsets.UTF_8)));
//        String password = passwordEncoder.encode("password");
//        System.out.println("비밀번호 : " + password);
//        System.out.println(Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8)));

        return passwordEncoder;
    }

    //    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        List list = new ArrayList<String>();
//        list.add("ROLE_ADMIN");
//        Member member = new Member("client", passwordEncoder().encode("password"),list);
//
//        userRepository.save(member);
//
//        auth.inMemoryAuthentication().withUser("client").password(passwordEncoder().encode("password")).roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("client2").password(passwordEncoder().encode("password")).roles("USER");
//    }
}
