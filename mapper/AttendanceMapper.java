package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.board.domain.AppAttendanceUserDTO;
import com.board.domain.AppAttendanceUserTimeDTO;
import com.board.domain.AttendanceUserDTO;

@Repository
@Mapper
public interface AttendanceMapper {
	
	public List<AttendanceUserDTO> getEntryList();

	public List<AttendanceUserDTO> getAbsenceList();
	
	public List<AttendanceUserDTO> getAttendanceAllUserList();
	
	public List<AppAttendanceUserTimeDTO> FindAttendanceList(AppAttendanceUserTimeDTO params);
	
	public AppAttendanceUserDTO findName(String name);
	 
	public List<AppAttendanceUserDTO> FindEmployeeData(AppAttendanceUserDTO params);

	public void EntryButton(Long eNo);

	public void ExitButton(Long eNo);
}
