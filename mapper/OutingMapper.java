package com.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.board.domain.AppOutingUserDTO;
import com.board.domain.AppOutingUserTimeDTO;
import com.board.domain.OutingUserDTO;

@Repository
@Mapper
public interface OutingMapper {

	public List<OutingUserDTO> getOutingList();

	public List<AppOutingUserTimeDTO> FindoutingList(AppOutingUserTimeDTO params);

	public List<AppOutingUserDTO> FindEmployeeData(AppOutingUserDTO params);

	public void returnButton(Long eNo);

	public void outingButton(Long eno, String reason);
}
