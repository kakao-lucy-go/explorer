package com.mychum1.explorer.config;

import com.mychum1.explorer.common.JwtProvider;
import com.mychum1.explorer.domain.Member;
import com.mychum1.explorer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Autowired
    public UserRepository userRepository;

    @Bean
    public JwtProvider jwtProvider() {return new JwtProvider();}

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/*");
    }

    @Override
    protected  void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                //.antMatchers("/","/api/*").hasRole("ADMIN")
                .antMatchers("/login","/asset").permitAll()  //login 화면은 누구든 접근 가능

                .and().formLogin().defaultSuccessUrl("/")
                .loginProcessingUrl("/loginProcess")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .and().exceptionHandling().accessDeniedPage("/denied")
                .and().csrf().disable();

//        http.httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/","/login").permitAll()
//                .antMatchers(HttpMethod.POST, "/login").permitAll()
//                .anyRequest().authenticated()
//                .and().exceptionHandling().accessDeniedPage("/login")
//                .and()
//                .addFilterBefore(new JwtFilter(jwtProvider()), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        List list = new ArrayList<String>();
        list.add("ROLE_ADMIN");
        Member member = new Member("client", passwordEncoder().encode("password"),list);

        userRepository.save(member);

        auth.inMemoryAuthentication().withUser("client").password(passwordEncoder().encode("password")).roles("ADMIN");
        auth.inMemoryAuthentication().withUser("client2").password(passwordEncoder().encode("password")).roles("USER");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*");
    }

}
