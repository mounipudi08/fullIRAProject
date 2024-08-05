package com.iraportal.accenture.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Trainings {
	private String course;
    private String duration;
    private String description;
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
}
