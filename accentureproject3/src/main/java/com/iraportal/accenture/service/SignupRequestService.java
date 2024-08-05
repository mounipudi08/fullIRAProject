package com.iraportal.accenture.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iraportal.accenture.model.User;
import com.iraportal.accenture.repository.UserRepository;

import java.util.List;

@Service
public class SignupRequestService {

    @Autowired
    private UserRepository userRepository;

    public boolean approveSignupRequest(Long id) {
        // Logic to approve the signup request
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setApproved(true); // Set the approved status to true
            user.setActive(true); // Set the active status to true
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean disapproveSignupRequest(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<User> getPendingSignupRequests() {
        return userRepository.findByApprovedFalse();
    }
}
