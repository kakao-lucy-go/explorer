package com.mychum1.explorer.common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//OncePerRequestFilter
@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider=jwtProvider;
    }

//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        request.requ
//        String token = jwtProvider.resolveToken((HttpServletRequest) request);  //토큰 추출
//        if(token != null && jwtProvider.validationToken(token)) {   //토큰이 유효한지 확인
//            //security context 에 인증 절차를 대체
//            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(token)); //인증 주체 변경
//        }
//        chain.doFilter(request, response);
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("call doFilterInternal");
        String path = request.getRequestURI();
        System.out.println(path);
        System.out.println(request.getHeader("Authorization"));

        //로그인은 그대로 통과
        if(path.equals("/login")){
            filterChain.doFilter(request,response);

        }else {
            //로그인이 아니면 토큰 검증
            String token = jwtProvider.resolveToken(request);
            if(token != null && jwtProvider.validationToken(token)) {
                System.out.println("유효함");
                filterChain.doFilter(request,response);

            }else {
                response.sendError(HttpStatus.FORBIDDEN.value(), "FORBIDDEN");
                return;
            }
        }

    }

//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        System.out.println("call doFilterInternal");
//        String path = request.u .getRequestURI();
//        System.out.println(path);
//        //로그인은 그대로 통과
//        if(path.equals("/login")){
//            filterChain.doFilter(request,response);
//
//        }else {
//            //로그인이 아니면 토큰 검증
//            String token = jwtProvider.resolveToken(request);
//            if(token != null && jwtProvider.validationToken(token)) {
//                filterChain.doFilter(request,response);
//
//            }else {
//                response.sendError(HttpStatus.FORBIDDEN.value(), "FORBIDDEN");
//                return;
//            }
//        }
//    }
}
