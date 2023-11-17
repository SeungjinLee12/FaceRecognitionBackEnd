package com.board.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.board.domain.AppAttendanceUserDTO;
import com.board.domain.AppAttendanceUserTimeDTO;
import com.board.domain.AttendanceUserDTO;
import com.board.domain.OutingUserDTO;
import com.board.domain.UserDTO;
import com.board.mapper.AttendanceMapper;
import com.board.mapper.OutingMapper;
import com.board.service.AttendanceService;
import com.board.service.UserInformationService;

@Controller
@RequestMapping("/api/attendance") // URL 변경 가능
public class AttendanceController {

	@Autowired
	private final AttendanceService attendanceService;

	@Autowired
	private final AttendanceMapper attendanceMapper;

	@Autowired
	private final OutingMapper outingMapper;

	@Autowired
	public AttendanceController(AttendanceService attendanceService, AttendanceMapper attendanceMapper,
			OutingMapper outingMapper) {
		this.attendanceService = attendanceService;
		this.attendanceMapper = attendanceMapper;
		this.outingMapper = outingMapper;
	}

	@GetMapping("/entry")
	@ResponseBody
	public Map<String, Object> getEntryUserList() {
		List<AttendanceUserDTO> userList = attendanceService.getEntryList();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", userList);
			System.out.println(
					"WEB--------------------------------------------------회원출석현황 호출--------------------------------------------------");
		} catch (Exception e) {
			e.getMessage();
		}
		return map;
	}

	@GetMapping("/absence")
	@ResponseBody
	public Map<String, Object> getAbsenceUserList() {
		List<AttendanceUserDTO> userList = attendanceService.getAbsenceList();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", userList);
			System.out.println(map);
			System.out.println(
					"WEB--------------------------------------------------회원결석현황 호출--------------------------------------------------");
		} catch (Exception e) {
			e.getMessage();
		}
		return map;
	}

	@GetMapping("/all")
	@ResponseBody
	public Map<String, Object> getAttendanceAllUserList() {
		List<AttendanceUserDTO> userList = attendanceService.getAttendanceAllUserList();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map.put("data", userList);
			System.out.println(
					"WEB--------------------------------------------------회원출석기록 호출--------------------------------------------------");
		} catch (Exception e) {
			e.getMessage();
		}
		return map;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GetMapping(value = "/attendance/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppAttendanceUserTimeDTO>> Attendance(
			@ModelAttribute("params") AppAttendanceUserTimeDTO params) {
		List<AppAttendanceUserTimeDTO> attent = attendanceMapper.FindAttendanceList(params);
		try {
			if (attent != null) {
				System.out.println(
						"APP--------------------------------------------------사원출석현황 호출--------------------------------------------------");
				System.out.println("안드로이드로 보낼 값: " + attent);
				return ResponseEntity.ok(attent);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/listdetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AppAttendanceUserDTO>> attendancedetails2(@RequestParam("name") String NAME,
			@ModelAttribute("params") AppAttendanceUserDTO params) {
		AppAttendanceUserDTO Name = attendanceMapper.findName(NAME);
		System.out.println(
				"APP--------------------------------------------------출석사원이름 호출--------------------------------------------------");

		try {
			if (NAME != null && Name.getNAME().equals(NAME)) {
				params.setNAME(NAME);
				List<AppAttendanceUserDTO> attendance = attendanceService.getEmployeeDetails(params);
				System.out.println(
						"APP-------------------------------------------------출석사원상세정보 호출---------------------------------------------------");
				return ResponseEntity.ok(attendance);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping(value = "/entry", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> EntryButton(@RequestBody String eNo){
		try {
			String numberString = eNo.replaceAll("\\D+", ""); // 숫자가 아닌 문자를 제거하고 "25"를 얻음
			Long ENo = Long.parseLong(numberString);
			System.out.println("APP--------------------------------------------------출근 : " + ENo
					+ "--------------------------------------------------");
			attendanceMapper.EntryButton(ENo);
			
			return ResponseEntity.ok("수정 성공");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ExitButton(@RequestBody String eNo){
		try {
			String numberString = eNo.replaceAll("\\D+", ""); // 숫자가 아닌 문자를 제거하고 "25"를 얻음
			Long ENo = Long.parseLong(numberString);
			System.out.println("APP--------------------------------------------------퇴근 : " + ENo
					+ "--------------------------------------------------");
			attendanceMapper.ExitButton(ENo);
			
			return ResponseEntity.ok("수정 성공");
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
