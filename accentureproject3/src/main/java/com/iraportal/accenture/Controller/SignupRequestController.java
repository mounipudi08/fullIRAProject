package com.iraportal.accenture.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.iraportal.accenture.service.SignupRequestService;

import java.util.List;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.service.SignupRequestService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SignupRequestController {

    @Autowired
    private SignupRequestService signupRequestService;

    @PostMapping("/signup-requests/{id}/approve")
    public ResponseEntity<String> approveSignupRequest(@PathVariable Long id) {
        boolean approved = signupRequestService.approveSignupRequest(id);
        if (approved) {
            return ResponseEntity.ok("Signup request approved");
        } else {
            return ResponseEntity.badRequest().body("Failed to approve signup request");
        }
    }

    @PostMapping("/signup-requests/{id}/disapprove")
    public ResponseEntity<String> disapproveSignupRequest(@PathVariable Long id) {
        boolean disapproved = signupRequestService.disapproveSignupRequest(id);
        if (disapproved) {
            return ResponseEntity.ok("Signup request disapproved");
        } else {
            return ResponseEntity.badRequest().body("Failed to disapprove signup request");
        }
    }

    @GetMapping("/signup-requests/pending")
    public ResponseEntity<List<User>> getPendingSignupRequests() {
        List<User> pendingRequests = signupRequestService.getPendingSignupRequests();
        return ResponseEntity.ok(pendingRequests);
    }
}

