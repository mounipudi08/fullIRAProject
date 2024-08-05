package com.iraportal.accenture.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.iraportal.accenture.model.Interview;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>{

	@Query("Select i from Interview i where i.interviewer.email = :email")
	List<Interview> getInterviewsByInterviewerId(@Param("email") String email);
	
	@Query("Select i.interviewer, i.completed, i.rating, i.feedback from Interview i where i.profile.id = :id")
	List getFeedbackById(@Param("id") Long id);
}