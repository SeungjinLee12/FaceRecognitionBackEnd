package com.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.UserDTO;
import com.board.mapper.UserInformationMapper;

@Service
public class UserInformationServiceImpl implements UserInformationService {

	private final UserInformationMapper userInformationMapper;

	@Autowired
	public UserInformationServiceImpl(UserInformationMapper userInformationMapper) {
		this.userInformationMapper = userInformationMapper;
	}

	@Override
	public List<UserDTO> getUserList() {
		return userInformationMapper.getList();
	}

	@Override
	public List<UserDTO> getWaitingJoinList() {
		return userInformationMapper.getWaitingJoinList();
	}

	@Override
	public void joinRejected(Object ob) {
	    if (ob instanceof List<?>) {
	        List<?> list = (List<?>) ob;
	        if (!list.isEmpty()) {
	            // List를 명시적으로 List<Map<String, Object>>로 캐스팅
	            List<Map<String, Object>> dataList = (List<Map<String, Object>>) list;
	            userInformationMapper.joinRejected(dataList);
	        }
	    }
	}
	
	@Override
	public void joinOk(Object ob) {
	    if (ob instanceof List<?>) {
	        List<?> list = (List<?>) ob;
	        if (!list.isEmpty()) {
	            // List를 명시적으로 List<Map<String, Object>>로 캐스팅
	            List<Map<String, Object>> dataList = (List<Map<String, Object>>) list;
	            userInformationMapper.joinOk(dataList);
	        }
	    }
	}
	
	@Override
	public void getModifyUser(String no,String name,String grade,String birth,String phone) {
		userInformationMapper.getModifyUser(no, name, grade, birth, phone);
	}
	
	@Override
	public void getDeleteUser(String no) {
		userInformationMapper.getDeleteUser(no);
	}

}
