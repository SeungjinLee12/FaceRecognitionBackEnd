package com.board.service;

import org.springframework.stereotype.Service;

import com.board.domain.UserDTO;

@Service
public interface LoginService {
	
	public UserDTO getUserById(String id);
}
