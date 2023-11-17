package com.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AppAttendanceUserDTO {

	@Id
	private String NAME;
	private String PHONE;
	private String GRADE;
	private String FILE_URL;
	private String EMAIL;


}
