package com.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;

@Service
public interface BoardService {
	// 정보 가져오기
	public List<BoardDTO> FindBoardList(BoardDTO params) throws Exception;

	public List<BoardDTO> FindNoticeList(BoardDTO params) throws Exception;

	public List<BoardDTO> appBoardList(BoardDTO params) throws Exception;

	public void boardUpdate(long no, String title, String content);

	public void inputBoard(String title, String content, String notice_YN, String eNo);

	public void boardDelete(long idx) throws Exception;

	public void inputWebNoticeBoard(String title, String content, String eNo);

	public void inputReply(String content, String eNo, long index);

	public int getIDX();

	public void attachfileAppUpdate(List<Map<String, Object>> fileData);

	public int getIDXApp(long no);

	public void filedelete(String fileurl);

	public void attachfileAppinsert(List<Map<String, Object>> fileData);

}