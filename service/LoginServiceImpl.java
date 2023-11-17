// LoginServiceImpl.java
package com.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.UserDTO;
import com.board.mapper.UserMapper;

@Service
public class LoginServiceImpl implements LoginService {
    
    private final UserMapper userMapper;

    @Autowired
    public LoginServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    public UserDTO getUserById(String id) {
        return userMapper.getUserById(id);
    }
    
}
