package com.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class OutingUserDTO {
	
	 @Id
	 private String NO;
	 private String NAME;
	 private String GRADE;
	 private String OUTING_DATE;
	 private String OUTING_TIME;
	 private String RETURN_TIME;
	 private String REASON;
}
