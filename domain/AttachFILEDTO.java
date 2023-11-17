package com.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class AttachFILEDTO {
	@Id
	private String NO;
	private Long BIDX;
	private String FILEURL;

}
