package com.iraportal.accenture.service;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.model.userRole;
import com.iraportal.accenture.repository.UserRepository;
import com.iraportal.accenture.security.jwt.JwtTokenProvider;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.iraportal.accenture.dto.LoginRequest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import com.iraportal.accenture.exceptions.UserNotActiveException;
import com.iraportal.accenture.exceptions.UserNotApprovedException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.iraportal.accenture.dto.LoginRequest;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider,
            SimpMessagingTemplate messagingTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public User initiateSignUp(User user, userRole role) {
        // Encode password before saving

        String encodedPassword = (user.getPassword());

        // Log encoded password
        System.out.println("Encoded password during signup: " + encodedPassword);
        // Set the role for the user
        user.setRole(role);

        // need to trigger the approval request to admin
        messagingTemplate.convertAndSend("/topic/admin", "New signup request from: " + user.getEmail());
        // Save the user
        return userRepository.save(user);
    }

    @Override
    public User approveSignUp(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        User user = optionalUser.get();

        // Assuming admin approval sets the user's role based on their requested role
        userRole requestedRole = user.getRole(); // The role requested during sign-up

        // Set the role for the user
        user.setRole(requestedRole);
        user.setApproved(true); // Mark user as approved

        // Save the updated user
        return userRepository.save(user);
    }

  
    @Override
    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail());
        System.out.println(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword()));

        if (user == null)
            throw new BadCredentialsException("Invalid email");
        System.out.println("Login attempt for user: " + user.getEmail());
        System.out.println("Retrieved hashed password from database: " + user.getPassword());
        System.out.println("Retrieved hashed password from user: " + passwordEncoder.encode(loginRequest.getPassword()));
        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("password");
        }
        if (!user.isActive()) {
            throw new UserNotActiveException("User account is not active");
        }

        if (!user.isApproved()) {
            throw new UserNotApprovedException("User account is not approved");
        }
        userRole role = user.getRole();

        // Map roles to authorities
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(getAuthorities(role))
                .build();

        return jwtTokenProvider.generateToken(userDetails,role);
    }


    @Override
    public User assignRoles(User user, userRole role) {
        user.setRole(role); // Set roles for the user
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(userRole role) {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
    
    public List<User> getAlldetails(){
  	  return userRepository.findAll();
    }
    
    public Optional<User> getEmployee(long id) {
  	  return userRepository.findById(id);
    }
    
    public User getEmployeeByEmail(String email) {
    	  return userRepository.findByEmail(email);
      }
    
    
    
//    public Optional<User> getEmployee(String email) {
//    	  return userRepository.find(email);
//      }
    
    public User updateByEmail(String email,User user) {
    	User emp = userRepository.findByEmail(email);
    	System.out.println(emp);
		emp.setName(user.getName());
		//emp.setEmail(user.getEmail());
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        System.out.println(hashedPassword);
        emp.setPassword(hashedPassword);
		return userRepository.save(emp);
    }
    
    
    
    
    public void deleteEmployee(long id)
    {
    	userRepository.deleteById(id);
   }
    
    @Transactional
    public void deleteByEmail(String email)
    {
    	userRepository.deleteByEmail(email);
   }
    
    public List findAllInterviewers() {
		List result = userRepository.findInterviewer();
		List<Map<String, Object>> interviewers = new ArrayList<>();
		for(Object obj: result) {
			Object[] objArray = (Object[]) obj;
			Map<String, Object> interviewer = new HashMap<>();
			interviewer.put("id", objArray[0]);
			interviewer.put("name", objArray[1]);
			interviewer.put("email", objArray[2]);
			interviewers.add(interviewer);
		}
		return interviewers;		
	}

}
