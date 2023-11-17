package com.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AttendanceUserDTO {

	@Id
	private String NO;
	private String NAME;
	private String GRADE;
	private String PHONE;
	private String ENTRY_DATE;
	private String ENTRY_TIME;
	private String EXIT_TIME;
}
