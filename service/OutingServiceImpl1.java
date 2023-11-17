//package com.board.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.board.domain.OutingDTO;
//import com.board.mapper.OutingMapper;
//
//@Service
//public class OutingServiceImpl implements OutingService{
//
//	@Autowired
//	private OutingMapper outingMapper;
//	@Override
//	public List<OutingDTO> getOuting(OutingDTO params) {
//		// TODO Auto-generated method stub
//		List<OutingDTO> out = outingMapper.FindoutingList(params);
//		if(out != null) {
//			return out;
//		}
//		return null;
//	}
//	
//}
