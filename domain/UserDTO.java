package com.board.domain;

import java.lang.reflect.Member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Component
public class UserDTO {

	@Id
	private String NO;
	private String ID;
	private String PASSWORD;
	private String STATE;
	private String NAME;
	private String BIRTH;
	private String JM;// 주민등록번호
	private String PHONE;
	private String GRADE;
	private String EMAIL;
	private String FILE_URL;
	private String FILE_PATH;
	private String FILEURL;
	public Member orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
