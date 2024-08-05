package com.iraportal.accenture.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Projects {
	private String title;
	private String technologies;
	private String description;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTechnologies() {
		return technologies;
	}
	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Projects [title=" + title + ", technologies=" + technologies + ", description=" + description + "]";
	}
	
}
