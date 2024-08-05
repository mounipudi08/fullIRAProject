package com.iraportal.accenture.Controller;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.security.jwt.JwtTokenProvider;
import com.iraportal.accenture.service.UserService;


import java.util.List;
import java.util.Optional;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.security.jwt.JwtTokenProvider;
import com.iraportal.accenture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.iraportal.accenture.dto.LoginRequest;
import com.iraportal.accenture.exceptions.UserNotActiveException;
import com.iraportal.accenture.exceptions.UserNotApprovedException;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public LoginController(UserService userService,JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = userService.login(loginRequest);
            return ResponseEntity.ok(token); 
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"Invalid email or password\"}");
        } catch (UserNotActiveException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"User account is not active\"}");
        } catch (UserNotApprovedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{\"message\": \"User account is not approved\"}");
        }
    }
    @GetMapping("home")
	public List<User> getAlldetails(){
		return userService.getAlldetails();
	}
	
//	@PostMapping("addEmployee")
//	public Employee adddetails(@RequestBody Employee employee) {
//		service.adddetails(employee);
//		//return service.getEmployee(employee.getEmployee());
//		 
//	}
	
	@GetMapping("employeedetail/{id}")
	public Optional<User> getEmployee(@PathVariable("id") long id) {
		  return userService.getEmployee(id);
	  }
	
	@GetMapping("employee/{email}")
	public User getEmployeeByEmail(@PathVariable("email") String email) {
		 return userService.getEmployeeByEmail(email);
	  }
	
	
	
//	@PutMapping("update/{email}")
//	 public User updateEmployee(@PathVariable("email") String email, @RequestBody User user) {
//		return userService.UpdateByEmail(user);
//		 
//	  }
	
	@PutMapping("/update/{email}")
	public User UpdateEmployee(@PathVariable("email") String email, @RequestBody User user) {
		
	    return userService.updateByEmail(email, user);
	}
	
	 @DeleteMapping("delete/{id}")
	 public void deleteEmployee(@PathVariable int id)
	  {
		 userService.deleteEmployee(id);
	  }
	 
	 @DeleteMapping("deleteEmp/{email}")
	 public void deleteEmployeeByEmail(@PathVariable String email)
	  {
		 userService.deleteByEmail(email);
	  }
	 
	 @GetMapping("/interviewers")
	 public List findAllinterviewers() {
		 return userService.findAllInterviewers();
	 }
	 
	 
}


