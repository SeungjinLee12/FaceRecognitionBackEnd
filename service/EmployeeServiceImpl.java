package com.board.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.board.domain.UserDTO;
import com.board.mapper.UserMapper;


@Service
public class EmployeeServiceImpl implements EmployeeService{

    private UserMapper userMapper;


    @Transactional
   public void join(UserDTO params) {
       userMapper.addEmployee(params);
   }

   public UserDTO getEmployeeName(String Name) {
      // TODO Auto-generated method stub
      return userMapper.getUserByName(Name);
   }




}