package com.iraportal.accenture.Controller;

import com.iraportal.accenture.dto.SignUpRequest;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            // Check if the user already exists
            if (userService.findByEmail(signUpRequest.getEmail()) != null) {
                return ResponseEntity.badRequest().body("User with this email already exists");
            }

            // Create a new user object from the signup request
            User newUser = new User();
            newUser.setName(signUpRequest.getName());
            newUser.setEmail(signUpRequest.getEmail());
            newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
            newUser.setApproved(false); // Set initial approval status to false

            // Call the UserService to initiate signup (save user with pending approval)
            userService.initiateSignUp(newUser, signUpRequest.getRole());

            // Return a success response with a message
            return ResponseEntity.ok()
                    .body("{\"message\": \"User signup initiated successfully! Waiting for admin approval.\"}");
        } catch (Exception e) {
            // Handle exceptions, e.g., duplicate user signup attempt
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"message\": \"Failed to initiate signup,\"}");
        }
    }

}
