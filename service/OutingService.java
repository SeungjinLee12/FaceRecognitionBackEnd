package com.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.board.domain.AppOutingUserDTO;
import com.board.domain.AppOutingUserTimeDTO;
import com.board.domain.OutingUserDTO;

@Service
public interface OutingService {

	public List<OutingUserDTO> getOutingList();

	public List<AppOutingUserTimeDTO> getAppOutingList(AppOutingUserTimeDTO params);

	public List<AppOutingUserDTO> getEmployeeDetails(AppOutingUserDTO params);

}
