package com.iraportal.accenture.repository;

import com.iraportal.accenture.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);
    
    void deleteByEmail(String email);
    
    List<User> findByApprovedFalse();
	
	@Query("Select u.id, u.name, u.email from User u where u.role='INTERVIEWER' and u.active=true and u.approved=true")
	List findInterviewer();
   
    
    }
