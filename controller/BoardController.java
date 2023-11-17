package com.board.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.AttachFILEDTO;
import com.board.domain.BoardDTO;
import com.board.mapper.BoardMapper;
import com.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class BoardController {

	@Autowired
	private BoardService boardservice;

	@Autowired
	private BoardMapper boardmapper;

	// 웹 게시판 리스트
	@GetMapping(value = "/board/list", produces = MediaType.APPLICATION_JSON_VALUE) // HTML 응답을 받을 수 있도록 설정
	public ResponseEntity<List<BoardDTO>> boardlist(@ModelAttribute("params") BoardDTO params) throws Exception {
		try {
			System.out.println(
					"WEB--------------------------------------------------게시판리스트--------------------------------------------------");
			List<BoardDTO> board = boardmapper.FindBoardList(params);
			if (board != null) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 웹 공지사항 리스트
	@GetMapping(value = "/board/noticelist", produces = MediaType.APPLICATION_JSON_VALUE) // HTML 응답을 받을 수 있도록 설정
	public ResponseEntity<List<BoardDTO>> noticeList(@ModelAttribute("params") BoardDTO params) throws Exception {
		try {
			// SQL 쿼리를 사용하여 게시글 조회
			List<BoardDTO> board = boardmapper.FindNoticeList(params);
			if (board != null) {
				System.out.println(
						"WEB--------------------------------------------------공지리스트 호출--------------------------------------------------");
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 웹 게시판 상세보기
	@GetMapping(value = "/board/list/{idx}", produces = MediaType.APPLICATION_JSON_VALUE) // HTML 응답을 받을 수 있도록 설정
	public ResponseEntity<List<BoardDTO>> boarddetail(@PathVariable("idx") long idx) throws Exception {
		try {

			boardmapper.viewcntUP(idx);
			List<BoardDTO> board = boardmapper.boardDetail(idx);
			System.out.println("WEB--------------------------------------------------게시판 상세보기" + idx
					+ "--------------------------------------------------");

			if (board != null) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 웹 공지사항 상세보기
	@GetMapping(value = "/board/noticelist/{idx}", produces = MediaType.APPLICATION_JSON_VALUE) // HTML 응답을 받을 수 있도록 설정
	public ResponseEntity<List<BoardDTO>> noticedetail(@PathVariable("idx") long idx) throws Exception {
		try {
			boardmapper.viewcntUP(idx);
			List<BoardDTO> board = boardmapper.noticeDetail(idx);
			System.out.println("WEB--------------------------------------------------공지사항 상세보기" + idx
					+ "--------------------------------------------------");
			System.out.println(board);
			if (board != null) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 웹 게시글/공지사항 삭제
	@PostMapping(value = "/board/boardDelete")
	public void boardDelete(@RequestBody Map<String, Long> requestData) throws Exception {
		try {
			Long idx = requestData.get("data");

			boardmapper.boardDeleteReply(idx);
			boardmapper.boardDeleteFile(idx);
			boardservice.boardDelete(idx);

			System.out.println("WEB--------------------------------------------------게시글 삭제" + idx
					+ "--------------------------------------------------");
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 웹 공지사항 작성
	@PostMapping("/board/inputNotice")
	public ResponseEntity<String> InputWebNoticeBoard(@RequestBody BoardDTO boardDTO) {
		try {
			String title = boardDTO.getTitle();
			String eNo = boardDTO.geteNo();
			String content = boardDTO.getContent();

			System.out.println("WEB--------------------------------------------------공지사항 작성+" + title + ", " + content
					+ ", " + eNo + "--------------------------------------------------");

			boardservice.inputWebNoticeBoard(title, content, eNo);
			return ResponseEntity.ok("글쓰기 성공");
		} catch (Exception e) {
			System.out.println("5");

			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/board/inputNotice")
	@ResponseBody
	public int getIDX() {
		int idx = boardservice.getIDX();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("WEB--------------------------------------------------idx값 보내기 : " + idx
				+ "--------------------------------------------------");

		return idx;
	}

	@PostMapping("/board/attachNotice")
	public void attachNotice(@RequestBody String request) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		List<String> fileList = new ArrayList<String>();
		try {
			map = om.readValue(request, Map.class);
			try {
				fileList = (List<String>) map.get("filePaths");
				if (fileList != null && fileList.isEmpty()) {
				} else {
					boardmapper.attachFILE(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 웹 공지사항 수정
	@PostMapping(value = "/board/noticeUpdate")
	public ResponseEntity<String> noticeUpdate(@RequestBody String request) {

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
		System.out.println(
				"WEB--------------------------------------------------공지사항 수정--------------------------------------------------");

		try {
			map = om.readValue(request, Map.class);
			Map<String, Object> boardData = (Map<String, Object>) map.get("board");
			int idx = (int) boardData.get("idx");
			List<Map<String, Object>> boardDFiles = (List<Map<String, Object>>) map.get("d_file");

			if (boardDFiles != null && !boardDFiles.isEmpty()) {

				List<String> fileNames = new ArrayList<>();
				for (Map<String, Object> fileData : boardDFiles) {
					String fileName = (String) fileData.get("file_NAME");
					fileNames.add(fileName);
				}

				Map<String, Object> paramMap = new HashMap<>();
				paramMap.put("idx", idx);
				paramMap.put("fileNames", fileNames);

				System.out.println(paramMap);
				boardmapper.noticeUpdate_DF(paramMap);
				System.out.println(paramMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		try {
			map = om.readValue(request, Map.class);

			Map<String, Object> boardData = (Map<String, Object>) map.get("board");
			int idx = (int) boardData.get("idx");
			List<Map<String, Object>> boardAFiles = (List<Map<String, Object>>) map.get("a_file");
			Map<String, Object> totalData = new HashMap<>();

			totalData.put("idx", idx);
			totalData.put("aFile", boardAFiles);

			if (boardAFiles != null && !boardAFiles.isEmpty()) {
				System.out.println(totalData);
				boardmapper.noticeUpdate_AF(totalData);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		try {
			map = om.readValue(request, Map.class);
			Map<String, Object> boardData = (Map<String, Object>) map.get("board");
			int idx = (int) boardData.get("idx");
			String title = (String) boardData.get("title");
			String content = (String) boardData.get("content");
			boardservice.boardUpdate(idx, title, content);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ResponseEntity.ok("게시글 수정 성공");
	}

	@PostMapping(value = "/board/replydelete")
	public ResponseEntity<String> replyDelete(@RequestBody BoardDTO boardDTO) {
		try {
			String reply_no = boardDTO.getReply_no();

			boardmapper.replyDelete(reply_no);
			System.out.println("WEB--------------------------------------------------댓글 삭제 : " + reply_no
					+ "--------------------------------------------------");
			return ResponseEntity.ok("댓글 삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 앱 게시판/공지사항 상세보기 + 조회수 증가
	@PostMapping(value = "/board/list/{idx}", produces = MediaType.APPLICATION_JSON_VALUE) // HTML 응답을 받을 수 있도록 설정
	public ResponseEntity<List<BoardDTO>> boarddetail1(@PathVariable("idx") long idx) throws Exception {
		try {
			boardmapper.viewcntUP(idx);
			List<BoardDTO> board = boardmapper.boardDetailAPP1(idx);
			System.out.println(board);
			System.out.println("APP--------------------------------------------------공지사항/게시판 상세보기 : " + idx
					+ "--------------------------------------------------");
			if (board != null) {
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 앱 게시판/공지사항 리스트 호출
	@GetMapping(value = "/board/appBoardList", produces = MediaType.APPLICATION_JSON_VALUE) // HTML 응답을 받을 수 있도록 설정
	public ResponseEntity<List<BoardDTO>> appBoardList(@ModelAttribute("params") BoardDTO params) throws Exception {
		try {
			// SQL 쿼리를 사용하여 게시글 조회
			List<BoardDTO> board = boardmapper.appBoardList(params);
			System.out.println(board);
			if (board != null) {
				System.out.println(
						"APP--------------------------------------------------게시판, 공지사항 리스트 호출--------------------------------------------------");
				return ResponseEntity.ok(board);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 앱 글쓰기
	@PostMapping("/board/inputboard")
	public ResponseEntity<Integer> InputBoard(@RequestParam("title") String title,
			@RequestParam("content") String content, @RequestParam("notice_YN") String notice_YN,
			@RequestParam("eNo") String eNo) {
		try {
			System.out.println(
					"APP--------------------------------------------------글쓰기--------------------------------------------------");
			boardservice.inputBoard(title, content, notice_YN, eNo);
			int idx = boardservice.getIDX();
			System.out.println("App--------------------------------------------------idx값 보내기 : " + idx
					+ "번 글쓰기 성공--------------------------------------------------");
			System.out.println("APP-------------------------------------------------공지사항 작성 : " + idx + ", 제목 : "
					+ title + ", content : " + content + "--------------------------------------------------");
			return ResponseEntity.ok(idx);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/board/inputboardFILE")
	public ResponseEntity<String> AppBoardAUpdate(@RequestBody String request) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			ObjectMapper om = new ObjectMapper();
			List<String> fileList = new ArrayList<String>();
			try {
				map = om.readValue(request, Map.class);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + map);
				try {
					fileList = (List<String>) map.get("files");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + fileList);
					if (fileList != null && fileList.isEmpty()) {
					} else {
						boardmapper.attachFILE(map);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return null;
	}

	// 앱 공지사항 수정
	  @PostMapping(value = "/board/noticeUpdateAPP", produces = MediaType.APPLICATION_JSON_VALUE)
	   public ResponseEntity<String> noticeUpdateAPP(@RequestBody Map<String, String> data) {
	       String no = data.get("idx");
	       String title = data.get("title");
	       String content = data.get("content");
	       String eNo = data.get("eNo");
	       
	      long idx = Long.parseLong(no);
	      

	      try {
	         System.out.println("APP-------------------------------------------------공지사항 수정 : " + idx + ", 제목 : "
	               + title + ", content : " + content + "--------------------------------------------------");
	         boardmapper.boardAppUpdate(data);
	         System.out.println("APP-------------------------------------------------공지사항 수정 성공 : " + idx + ", 제목 : "
	               + title + ", content : " + content + "--------------------------------------------------");
	         return ResponseEntity.ok("수정 성공");

	      } catch (Exception e) {
	         e.printStackTrace();
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	      }
	   }

	// 앱 게시글 삭제
	@PostMapping(value = "/board/boardDeleteAPP")
	public void boardDelete(@RequestBody String idx) throws Exception {
		try {
			Long index = Long.parseLong(idx);

			boardmapper.boardDeleteReplyAPP(idx);
			boardmapper.boardDeleteFileAPP(idx);
			boardservice.boardDelete(index);
			System.out.println("APP--------------------------------------------------게시글 삭제 : " + idx
					+ "--------------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@PostMapping(value = "/board/reply")
	public ResponseEntity<String> inputReply(@RequestParam("content") String content, @RequestParam("eNo") String eNo,
			@RequestParam("idx") String idx) {
		try {
			System.out.println("APP-------------------------------------------------댓글등록 " + eNo + ", " + content + ", "
					+ idx + "-------------------------------------------------");

			Long index = Long.parseLong(idx);
			boardservice.inputReply(content, eNo, index);

			return ResponseEntity.ok("댓글 게시 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/board/replydeleteAPP")
	public ResponseEntity<String> replyDeleteAPP(@RequestParam("no") String no) {
		try {
			boardmapper.replyDelete(no);
			System.out.println("APP--------------------------------------------------댓글 삭제 : " + no
					+ "--------------------------------------------------");
			return ResponseEntity.ok("댓글 삭제 성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 파일 저장
	@PostMapping(value = "/board/fileupload_app", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatefile(@RequestBody String request) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			ObjectMapper om = new ObjectMapper();
			List<String> fileList = new ArrayList<String>();
			try {
				map = om.readValue(request, Map.class);
				String idx = (String) map.get("idx");
				boardmapper.boardDeleteFileAPP(idx);
				System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + map);
				System.out.println(idx);
				try {
					fileList = (List<String>) map.get("files");
					System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + fileList);
					if (fileList != null && fileList.isEmpty()) {
					} else {
						boardmapper.attachUpdateFILE(map);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return null;

	}

}