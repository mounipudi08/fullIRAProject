package com.iraportal.accenture.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iraportal.accenture.model.Interview;
import com.iraportal.accenture.repository.InterviewRepository;


@Service
public class InterviewService {

	@Autowired
	private InterviewRepository interviewRepository;
	
	public Interview saveInterview(Interview interview) {
		return interviewRepository.save(interview);
	}
	
	public List<Interview> getInterviews(String email) {
		return interviewRepository.getInterviewsByInterviewerId(email);
	}
	
	public Interview getInterviewById(Long id) {
		Optional<Interview> interview = interviewRepository.findById(id);
		
		if(interview.isPresent()) {
			return interview.get();
		}
		else {
			return null;
		}
	}
	
	public Interview saveFeedback(Interview interview) {
		Interview interviewResult = getInterviewById(interview.getId());
		interviewResult.setCompleted(interview.getCompleted());
		interviewResult.setRating(interview.getRating());
		interviewResult.setFeedback(interview.getFeedback());
		return interviewRepository.save(interviewResult);
			
	}
	
	public List getFeedbackById(Long id) {
		List result = interviewRepository.getFeedbackById(id);
		List<Map<String, Object>> feedbacks = new ArrayList<>();
		for(Object obj: result) {
			Object[] objArray = (Object[]) obj;
			Map<String, Object> feedback = new HashMap<>();
			feedback.put("interviewer", objArray[0]);
			feedback.put("completed", objArray[1]);
			feedback.put("rating", objArray[2]);
			feedback.put("feedback", objArray[3]);
			feedbacks.add(feedback);
		}
		return feedbacks;
	}
}
