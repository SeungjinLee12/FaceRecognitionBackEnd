package com.board.controller;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.board.domain.UserDTO;
import com.board.mapper.AttendanceMapper;
import com.board.mapper.OutingMapper;
import com.board.mapper.UserMapper;
import com.board.service.AttendanceService;
import com.board.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private UserMapper userMapper;
	
	private final PasswordEncoder passwordEncoder;
//	
	public LoginController(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

	Pattern emailPattern = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
			+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

	@RestController
	@RequestMapping("/api") // URL 변경 가능
	public class LoginUser {
		@PostMapping("/loginWeb")
		public ResponseEntity<UserDTO> handleLoginRequesttest(@RequestBody UserDTO userDTO) {
			try {
				String id = userDTO.getID(); // 클라이언트에서 보낸 id 값
				String pass = userDTO.getPASSWORD(); // 클라이언트에서 보낸 password 값

				// UserService를 사용하여 사용자 정보를 가져옵니다.
				UserDTO user = loginService.getUserById(id);

				System.out.println(
						"WEB--------------------------------------------------입력된 ID : " + id + " ,입력된 PASSWORD : "
								+ pass + ", " + user + "--------------------------------------------------");

				if (user != null && passwordEncoder.matches(pass,user.getPASSWORD()) && user.getSTATE().equals("2")) {

					return ResponseEntity.ok(user);
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		}

		@PostMapping(value = "/loginApp", produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<UserDTO> handleLoginRequesttest1(@RequestParam("id") String ID,
				@RequestParam("pw") String PW) {
			try {

				// UserService를 사용하여 사용자 정보를 가져옵니다.
				UserDTO user = loginService.getUserById(ID);
				System.out.println(
						"APP--------------------------------------------------입력된 ID : " + ID + " ,입력된 PASSWORD : " + PW
								+ ", " + user + "--------------------------------------------------");

				if (user != null && passwordEncoder.matches(PW,user.getPASSWORD())) {
					return ResponseEntity.ok(user);
				} else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}

		}

		@RequestMapping(value = "/board/android/checkDuplicate", produces = MediaType.APPLICATION_JSON_VALUE, method = {RequestMethod.GET, RequestMethod.POST})
		public ResponseEntity<String> checkDuplicate(@RequestParam("id") String id) {
			System.out.println("APP-------------------------------------------------회원가입 중복체크 : " + id
					+ "-------------------------------------------------");
			try {
				// 중복 아이디 체크를 수행하는 SQL 쿼리를 실행하고 결과를 가져옵니다.
				Integer count = userMapper.checkDuplicate(id);

				if (count != null && count > 0) {
					// 이미 사용 중인 아이디인 경우
					return ResponseEntity.ok("Failure");
				} else {
					// 중복 아이디가 아닌 경우
					return ResponseEntity.ok("Success");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
			}
		}

		@PostMapping(value = "/board/android/join")
		public ResponseEntity<String> join(@RequestBody UserDTO userDTO) {
			System.out.println(userDTO);
			try {
				String name = userDTO.getNAME();
				String birth = userDTO.getBIRTH();
				String jumin = userDTO.getJM();
				String phone = userDTO.getPHONE();
				String pw = userDTO.getPASSWORD();
				String pass = passwordEncoder.encode(pw);
				String id = userDTO.getID();
				String email = userDTO.getEMAIL();
				String FILE_URL = userDTO.getFILEURL();
				System.out.println(
						"APP-------------------------------------------------회원가입-------------------------------------------------");
				if (FILE_URL == null) {
					return ResponseEntity.badRequest().body("imageUrl이 누락되었습니다.");
				}

				if (phone == null || !phone.matches("010\\d{8}")) {
					return ResponseEntity.badRequest().body("유효하지 않은 폰 번호입니다.");
				}

				if (birth.length() != 6 || jumin.length() != 7) {
					return ResponseEntity.badRequest().body("유효하지 않은 주민등록번호입니다.");
				}

				char firstChar = jumin.charAt(0);
				if (firstChar != '1' && firstChar != '2' && firstChar != '3' && firstChar != '4') {
					return ResponseEntity.badRequest().body("유효하지 않은 주민등록번호입니다.");
				}

				if (!birth.matches("\\d{6}") || !jumin.substring(1).matches("\\d{6}")) {
					return ResponseEntity.badRequest().body("유효하지 않은 주민등록번호입니다.");
				}

				if (email == null || !emailPattern.matcher(email).matches()) {
					return ResponseEntity.badRequest().body("유효하지 않은 이메일 주소입니다.");
				}

				Integer count = userMapper.checkDuplicate(id);
				if (count != null && count > 0) {
					return ResponseEntity.ok("중복");
				} else {
					userMapper.addEmployee(name, birth, jumin, phone, pass, id, email, FILE_URL);
					return ResponseEntity.ok("Success");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
			}
		}

	}

}
