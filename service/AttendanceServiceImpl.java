package com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.AppAttendanceUserDTO;
import com.board.domain.AppAttendanceUserTimeDTO;
import com.board.domain.AttendanceUserDTO;
import com.board.mapper.AttendanceMapper;

@Service
public class AttendanceServiceImpl implements AttendanceService {
	
	private final AttendanceMapper attendanceMapper;
	
	@Autowired
	public AttendanceServiceImpl(AttendanceMapper attendanceMapper) {
		this.attendanceMapper = attendanceMapper;
	}

	@Override
	public List<AttendanceUserDTO> getEntryList() {
		return attendanceMapper.getEntryList();
	}
	
	@Override
	public List<AttendanceUserDTO> getAbsenceList() {
		return attendanceMapper.getAbsenceList();
	}
	@Override
	public List<AttendanceUserDTO> getAttendanceAllUserList() {
		return attendanceMapper.getAttendanceAllUserList();
	}
	
	@Override
	public List<AppAttendanceUserTimeDTO> getAttendance(AppAttendanceUserTimeDTO params) {
		// TODO Auto-generated method stub
		List<AppAttendanceUserTimeDTO> attent = attendanceMapper.FindAttendanceList(params);
		if(attent != null) {
			return attent;
		}
		return null;
	}
	
	@Override
	public AppAttendanceUserDTO getEmployeeName(String NAME) {
		return attendanceMapper.findName(NAME);
	}

	@Override
	public List<AppAttendanceUserDTO> getEmployeeDetails(AppAttendanceUserDTO params) {
		// TODO Auto-generated method stub
		List<AppAttendanceUserDTO> attent = attendanceMapper.FindEmployeeData(params);
		if(attent != null) {
			return attent;
		}
		return null;
	}
	
}
