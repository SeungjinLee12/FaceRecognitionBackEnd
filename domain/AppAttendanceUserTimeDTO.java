package com.board.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AppAttendanceUserTimeDTO {

	@Id
	private String NAME ;
	private LocalDateTime ENTRY;
	private LocalDateTime EXIT_;

}
