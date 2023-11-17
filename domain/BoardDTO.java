package com.board.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id; // 올바른 @Id 어노테이션 사용
import lombok.Data;

@Entity
@Data
public class BoardDTO {

	@Id
	private Long idx; // 번호 (PK)
	private String title; // 제목
	private String content; // 내용
	private int view_cnt; // 조회 수
	private String notice_YN; // 공지 여부
	private String insert_time;
	private String insert_date;
	private String Name; // 사원번호
	private String eNo;
	private String board_title;
	private String board_content;
	private String board_insert_date;
	private String board_insert_time;
	private String employee_name;
	private String employee_reply_name;
	private String reply_no;
	private String reply_content;
	private String reply_update_time;
	private String FILE_URL;
	private String FILE_NAME;
	
	public String geteNo() {
		return eNo;
	}
	public void seteNo(String eNo) {
		this.eNo = eNo;
	}

}
