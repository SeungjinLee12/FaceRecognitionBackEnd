package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.board.domain.AppAttendanceUserDTO;
import com.board.domain.AppAttendanceUserTimeDTO;
import com.board.domain.AttendanceUserDTO;

@Service
public interface AttendanceService {

	public List<AttendanceUserDTO> getEntryList();

	public List<AttendanceUserDTO> getAbsenceList();

	public List<AttendanceUserDTO> getAttendanceAllUserList();
	
	public List<AppAttendanceUserTimeDTO> getAttendance(AppAttendanceUserTimeDTO params);

	public AppAttendanceUserDTO getEmployeeName(String NAME);
	
	public List<AppAttendanceUserDTO> getEmployeeDetails(AppAttendanceUserDTO params);

}
