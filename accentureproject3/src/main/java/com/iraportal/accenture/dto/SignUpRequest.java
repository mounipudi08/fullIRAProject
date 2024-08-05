package com.iraportal.accenture.dto;

import com.iraportal.accenture.model.userRole;

public class SignUpRequest {

    private String name;
    private String email;
    private String password;
    private userRole role; // Updated to UserRole enum
    private boolean approved; // Added for approval status

    // Constructors
    public SignUpRequest() {
    }

    public SignUpRequest(String name, String email, String password, userRole role, boolean approved) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.approved = approved;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public userRole getRole() {
        return role;
    }

    public void setRole(userRole role) {
        this.role = role;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
