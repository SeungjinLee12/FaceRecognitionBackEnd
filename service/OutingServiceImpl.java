package com.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.board.domain.AppOutingUserDTO;
import com.board.domain.AppOutingUserTimeDTO;
import com.board.domain.OutingUserDTO;
import com.board.mapper.OutingMapper;

@Service
public class OutingServiceImpl implements OutingService {

	private final OutingMapper outingMapper;

	@Autowired
	public OutingServiceImpl(OutingMapper outingMapper) {
		this.outingMapper = outingMapper;
	}

	@Override
	public List<OutingUserDTO> getOutingList() {
		return outingMapper.getOutingList();
	}

	@Override
	public List<AppOutingUserTimeDTO> getAppOutingList(AppOutingUserTimeDTO params) {
		List<AppOutingUserTimeDTO> attent = outingMapper.FindoutingList(params);
		if (attent != null) {
			return attent;
		}
		return null;
	}

	@Override
	public List<AppOutingUserDTO> getEmployeeDetails(AppOutingUserDTO params) {
		// TODO Auto-generated method stub
		List<AppOutingUserDTO> attent = outingMapper.FindEmployeeData(params);
		if (attent != null) {
			return attent;
		}
		return null;
	}
}
