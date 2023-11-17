package com.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.UserDTO;
import com.board.mapper.UserInformationMapper;
import com.board.service.UserInformationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/api/userinformation") // URL 변경 가능
public class UserInformationController {

	private final UserInformationService userInformationService;
	
	private final UserInformationMapper userInformationMapper;

	@Autowired
	public UserInformationController(UserInformationService userInformationService, UserInformationMapper userInformationMapper) {
		this.userInformationService = userInformationService;
		this.userInformationMapper = userInformationMapper;
	}

	@GetMapping("/userlist")
	@ResponseBody
	public Map<String, Object> getUserList() {
		List<UserDTO> userList = userInformationService.getUserList();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", userList);
			System.out.println(
					"WEB--------------------------------------------------유저리스트 호출--------------------------------------------------");
		} catch (Exception e) {
			e.getMessage();
		}

		return map;
	}

	@GetMapping("/waitingJoinlist")
	@ResponseBody
	public Map<String, Object> getWaitingJoinList() {
		List<UserDTO> userList = userInformationService.getWaitingJoinList();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", userList);
			System.out.println(
					"WEB--------------------------------------------------대기자명단 호출--------------------------------------------------");
		} catch (Exception e) {
			e.getMessage();
		}

		return map;
	}

	@DeleteMapping("/joincheck")
	public ResponseEntity<String> joinRejected(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException, JSONException {

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		JSONObject jsonData = new JSONObject();

		try {
			map = om.readValue(request, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object ob = map.get("request");
		System.out.println("WEB--------------------------------------------------승인거절 명단 : " + ob
				+ "--------------------------------------------------");

		userInformationService.joinRejected(ob);
		return ResponseEntity.ok("test");
	}

	@PutMapping("/joincheck")
	public ResponseEntity<String> joinOk(@RequestBody String request)
			throws JsonMappingException, JsonProcessingException, JSONException {

		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper om = new ObjectMapper();
		JSONObject jsonData = new JSONObject();

		try {
			map = om.readValue(request, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Object ob = map.get("request");
		System.out.println("WEB--------------------------------------------------회원가입 승인명단 : " + ob
				+ "--------------------------------------------------");

		userInformationService.joinOk(ob);
		return ResponseEntity.ok("");
	}

	@PutMapping("/userlist")
	public ResponseEntity<String> UserInformationModify(@RequestBody UserDTO userDTO) {
		try {
			String no = userDTO.getNO();
			String name = userDTO.getNAME();
			String grade = userDTO.getGRADE();
			String birth = userDTO.getBIRTH();
			String phone = userDTO.getPHONE();

			System.out.println("수정  : " + no + ", " + name + ", " + grade + ", " + birth + ", " + phone);

			userInformationService.getModifyUser(no, name, grade, birth, phone);
			return ResponseEntity.ok("회원수정성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@DeleteMapping("/userlist")
	public ResponseEntity<String> UserInformationDelete1(@RequestBody UserDTO userDTO) {
		try {
			String no = userDTO.getNO();

			System.out.println("WEB--------------------------------------------------삭제할 회원 : " + no
					+ "--------------------------------------------------");
			userInformationMapper.getDeleteUserAttenDance(no);
			userInformationMapper.getDeleteUserBoard(no);
			userInformationMapper.getDeleteUserRefly(no);
			userInformationMapper.getDeleteUserOuting(no);
			
			
			userInformationService.getDeleteUser(no);
			return ResponseEntity.ok("회원삭제성공");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
