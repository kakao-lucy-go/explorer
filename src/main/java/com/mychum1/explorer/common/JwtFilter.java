package com.mychum1.explorer.common;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class JwtFilter extends GenericFilterBean {

    private JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider=jwtProvider;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = jwtProvider.resolveToken((HttpServletRequest) request);  //토큰 추출
        if(token != null && jwtProvider.validationToken(token)) {   //토큰이 유효한지 확인
            //security context 에 인증 절차를 대체
            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token)); //인증 주체 변경
        }
        chain.doFilter(request, response);
    }
}
