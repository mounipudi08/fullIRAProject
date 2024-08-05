package com.iraportal.accenture.service;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.model.userRole;

import java.util.List;
import java.util.Optional;

import com.iraportal.accenture.dto.LoginRequest;

public interface UserService {
	User initiateSignUp(User user, userRole roleName);

    User approveSignUp(Long userId);
    
    //User signUp(User User);

    String login(LoginRequest loginRequest);
    
    User assignRoles(User user, userRole role);

    User findByEmail(String email);
    
   Optional<User> getEmployee(long id);
   
   User getEmployeeByEmail(String email);
    
   // void updateEmployee(User user);
    
    void deleteEmployee(long id);
    
    void deleteByEmail(String email);
    
    User updateByEmail(String email,User user);

	List<User> getAlldetails();
	
	List findAllInterviewers();

}
