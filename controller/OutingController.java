package com.board.controller;

import java.util.Collections;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.board.domain.AppOutingUserDTO;
import com.board.domain.AppOutingUserTimeDTO;
import com.board.domain.BoardDTO;
import com.board.domain.UserDTO;
import com.board.mapper.OutingMapper;
import com.board.mapper.UserMapper;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.OutingUserDTO;
import com.board.service.OutingService;

@Controller
@RequestMapping("/api/outing") // URL 변경 가능
public class OutingController {

	private final OutingService outingService;
	private final OutingMapper outingMapper;
	private final UserMapper userMapper;
	private final AppOutingUserDTO appOutingUserDTO = null;
	

	@Autowired
	public OutingController(OutingService outingService, OutingMapper outingMapper, UserMapper userMapper) {
		this.outingService = outingService;
		this.outingMapper = outingMapper;
		this.userMapper = userMapper;

	}

	@GetMapping("/outinglist")
	@ResponseBody
	public Map<String, Object> getOutingUserList() {
		List<OutingUserDTO> userList = outingService.getOutingList();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", userList);
			System.out.println(
					"WEB--------------------------------------------------출타자리스트 호출--------------------------------------------------");
		} catch (Exception e) {
			e.getMessage();
		}

		return map;
	}

	@GetMapping(value = "/App/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppOutingUserTimeDTO>> Outing(@ModelAttribute("params") AppOutingUserTimeDTO params) {
		List<AppOutingUserTimeDTO> out = outingMapper.FindoutingList(params);
		try {
			if (out != null) {
				System.out.println(
						"APP--------------------------------------------------외출회원현황 호출--------------------------------------------------");
				System.out.println("안드로이드로 보낼 값: " + out);
				return ResponseEntity.ok(out);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/listdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppOutingUserDTO>> attendancedetails2(@RequestParam("name") String NAME,
			@ModelAttribute("params") AppOutingUserDTO params) {
		UserDTO Name = userMapper.getUserByName(NAME);
		System.out.println(
				"APP--------------------------------------------------외출사원이름 호출--------------------------------------------------");
		System.out.println("외출 상세 사원 이름 가져오는지 확인: " + Name);

		try {
			if (NAME != null && Name.getNAME().equals(NAME)) {
				params.setNAME(NAME);
				List<AppOutingUserDTO> outing = outingService.getEmployeeDetails(params);
				System.out.println(
						"APP-------------------------------------------------외출사원상세정보 호출---------------------------------------------------");
				System.out.println("안드로이드로 보낼 값: " + outing);
				return ResponseEntity.ok(outing);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping(value = "/outing", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppOutingUserDTO>> OutingButoon(@ModelAttribute("params") AppOutingUserDTO params) throws Exception {
		try {
			
			System.out.println(params);
			
			String ENO = params.getENo();
			
			Long ENo = Long.parseLong(ENO);
			String Reason = params.getREASON();
			
			System.out.println("APP--------------------------------------------------외출 : " + ENO + "사유 : "+Reason
					+ "--------------------------------------------------");
			
			outingMapper.outingButton(ENo, Reason);
			
			return ResponseEntity.ok(Collections.emptyList()); // 빈 목록을 반환하거나 원하는 데이터를 목록으로 감싸서 반환
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping(value = "/return", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ReturnButton(@RequestBody String request){
		try {
			Long ENo = Long.parseLong(request);
			System.out.println("APP--------------------------------------------------복귀 : " + ENo
					+ "--------------------------------------------------");
			outingMapper.returnButton(ENo);
			
			return ResponseEntity.ok("수정 성공");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	

}