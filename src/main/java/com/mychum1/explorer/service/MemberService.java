//package com.mychum1.explorer.service;
//
//import com.mychum1.explorer.domain.Member;
//import com.mychum1.explorer.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MemberService implements UserDetailsService {
//
//
//    @Autowired
//    private UserRepository repository;
//
//
//    @Override
//    public Member loadUserByUsername(String username) throws UsernameNotFoundException {
//        return repository.findById(username).get();
//    }
//}
