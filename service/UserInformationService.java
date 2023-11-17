package com.board.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.board.domain.UserDTO;


@Service
public interface UserInformationService {
	
	public List<UserDTO> getUserList();

	public List<UserDTO> getWaitingJoinList();

	public void joinRejected(Object ob);
	
	public void joinOk(Object ob);
	
	public void getModifyUser(String no,String name,String grade,String birth,String phone);

	public void getDeleteUser(String no);
}
