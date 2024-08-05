package com.iraportal.accenture.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iraportal.accenture.model.Interview;
import com.iraportal.accenture.service.InterviewService;

@RestController
public class InterviewController {

	@Autowired
	private InterviewService interviewService;
	
	@PostMapping("/saveInterview")
	public Interview saveInterview(@RequestBody Interview interview) {
		return interviewService.saveInterview(interview);
	}
	
	@GetMapping("/getInterviews/{email}")
	public List<Interview> getInterviews(@PathVariable("email") String email) {
		return interviewService.getInterviews(email);
	}
	
	@PutMapping("/saveFeedback")
	public ResponseEntity<Interview> saveFeedback(@RequestBody Interview interview) {
		Interview response = interviewService.getInterviewById(interview.getId());
		if(response != null) {
			Interview result = interviewService.saveFeedback(interview);
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
//	@GetMapping("/getFeedback/{id}")
//	public ResponseEntity<List> getFeedbackById(@PathVariable("id") Long id) {
//		Interview response = interviewService.getInterviewById(id);
//		System.out.println(response);
//		if(response != null) {
//			List result = interviewService.getFeedbackById(id);
//			return ResponseEntity.ok(result);
//		}
//		else {
//			return ResponseEntity.notFound().build();
//		}
//	}
	@GetMapping("/getFeedback/{id}")
	public List getFeedbackById(@PathVariable("id") Long id) {
			List result = interviewService.getFeedbackById(id);
			return result;
	}
}
