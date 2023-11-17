package com.board.service;

import com.board.domain.UserDTO;


public interface EmployeeService {

   public void join(UserDTO params) throws Exception;
   
   public UserDTO getEmployeeName(String Name);

}