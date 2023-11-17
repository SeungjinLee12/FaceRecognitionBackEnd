package com.board.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.board.domain.UserDTO;

@Repository
@Mapper
public interface UserMapper {
	
	public UserDTO getUserById(String id);

	public Integer checkDuplicate(String id);

	public void addEmployee(@Param("name") String name,@Param("birth") String birth,@Param("jumin") String jumin,@Param("phone") String phone,@Param("pw") String pw,@Param("id") String id,@Param("email") String email,@Param("FILE_URL") String FILE_URL);

	public void addEmployee(UserDTO params);

	public UserDTO getUserByName(String name);
}
