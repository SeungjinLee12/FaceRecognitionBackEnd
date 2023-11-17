package com.board.configuration;

import java.lang.reflect.Member;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.board.domain.User;
import com.board.domain.UserDTO;
import com.board.service.LoginService;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final LoginService loginService;

    public MyUserDetailsService(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public UserDetails loadUserByUsername(String ID) throws UsernameNotFoundException {
        UserDTO findOne = loginService.getUserById(ID);
        return (UserDetails) findOne;
    }
}