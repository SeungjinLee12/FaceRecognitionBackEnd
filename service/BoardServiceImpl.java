package com.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardmapper;

	@Override
	public List<BoardDTO> FindBoardList(BoardDTO params) {
		// 조회 결과 반환
		List<BoardDTO> board = boardmapper.FindBoardList(params);
		if (board != null) {
			return board;
		}
		return null;
	}

	@Override
	public List<BoardDTO> FindNoticeList(BoardDTO params) throws Exception {
		// TODO Auto-generated method stub
		List<BoardDTO> board = boardmapper.FindNoticeList(params);
		if (board != null) {
			return board;
		}
		return null;
	}

	@Override
	public List<BoardDTO> appBoardList(BoardDTO params) throws Exception {
		List<BoardDTO> board = boardmapper.appBoardList(params);
		if (board != null) {
			return board;
		}
		return null;
	}

	@Override
	public void boardDelete(long idx) throws Exception {
		boardmapper.boardDelete(idx);
	}

	@Override
	public void boardUpdate(long no, String title, String content) {
		boardmapper.boardUpdate(no, title, content);
	}

	@Override
	public void inputBoard(String title, String content, String notice_YN, String eNo) {
		boardmapper.inputBoard(title, content, notice_YN, eNo);
	}

	@Override
	public void inputWebNoticeBoard(String title, String content, String eNo) {
		boardmapper.inputWebNoticeBoard(title, content, eNo);

	}

	@Override
	public void inputReply(String content, String eNo, long index) {
		boardmapper.inputReply(content, eNo, index);

	}

	@Override
	public int getIDX() {
		return boardmapper.getIDX();
	}

	@Override
	public int getIDXApp(long no) {
		return boardmapper.getIDXApp(no);
	}

	@Override
	public void filedelete(String fileurl) {
		// TODO Auto-generated method stub
		boardmapper.filedelete(fileurl);
	}

	@Override
	public void attachfileAppUpdate(List<Map<String, Object>> fileData) {
		boardmapper.attachfileAppUpdate(fileData);
	}

	@Override
	public void attachfileAppinsert(List<Map<String, Object>> fileData) {
		boardmapper.attachfileAppinsert(fileData);
	}

}