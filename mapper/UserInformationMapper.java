package com.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.board.domain.UserDTO;


@Repository
@Mapper
public interface UserInformationMapper {
	
	public List<UserDTO> getList();

	public List<UserDTO> getWaitingJoinList();
	
	public void joinRejected(List<Map<String, Object>> dataListList);
	
	public void joinOk(List<Map<String, Object>> dataListList);

	public void getModifyUser(@Param("no") String no,@Param("name") String name,@Param("grade") String grade,@Param("birth") String birth,@Param("phone") String phone);
	
	public void getDeleteUser(String no);

	public void getDeleteUserAttenDance(String no);

	public void getDeleteUserBoard(String no);

	public void getDeleteUserRefly(String no);

	public void getDeleteUserOuting(String no);
}
