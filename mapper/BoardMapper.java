package com.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.board.domain.AttachFILEDTO;
import com.board.domain.BoardDTO;

@Repository
@Mapper
public interface BoardMapper {

	public List<BoardDTO> FindBoardList(BoardDTO params);

	public List<BoardDTO> FindNoticeList(BoardDTO params);

	public List<BoardDTO> boardDetail(long idx);

	public List<BoardDTO> boardDetailAPP1(long idx);

	public List<BoardDTO> noticeDetail(long idx);

	public List<BoardDTO> appBoardList(BoardDTO params);

	public void boardDelete(long idx);

	public void boardUpdate(@Param("no") long no, @Param("title") String title, @Param("content") String content);

	public void inputBoard(String title, String content, String notice_YN, String eNo);

	public void inputWebNoticeBoard(@Param("title") String title, @Param("content") String content,
			@Param("eNo") String eNo);

	public void viewcntUP(long idx);

	public void inputReply(@Param("content") String content, @Param("eNo") String eNo, @Param("index") long index);

	public void replyDelete(String reply_no);

	public List<AttachFILEDTO> attachFILEURL(AttachFILEDTO attachFILEDTO);

	public int getIDX();

	public void attachFILE(Map<String, Object> map);

	public void boardDeleteReply(Long idx);

	public void boardDeleteFile(Long idx);

	public void boardDeleteReplyAPP(String idx);

	public void boardDeleteFileAPP(String idx);

	public void noticeUpdate_AF(Map<String, Object> paramMap);

	public void noticeUpdate_DF(Map<String, Object> paramMap);

	public int getIDXApp(long no);

	public void attachfileAppUpdate(List<Map<String, Object>> fileData);

	public void attachfileAppinsert(List<Map<String, Object>> fileData);

	public void filedelete(String fileurl);

	public void attachUpdateFILE(Map<String, Object> map);

	public void boardAppUpdate(Map<String, String> data);

}