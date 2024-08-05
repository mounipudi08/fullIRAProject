package com.iraportal.accenture.model;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name="employee")
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="profile_id")
	private int id;
	@Column(name="name")
	private String name;
	@Column(name="title")
	private String title;
	@Column(name="about")
	private String about;
	@Column(name="availability")
	private String availability;
	
	@Embedded
    private Contact contact;
	@ElementCollection
    @CollectionTable(name = "education",joinColumns =@JoinColumn(name="profile_id"))
	private List<Education> education;
	@ElementCollection
    @CollectionTable(name = "trainings",joinColumns =@JoinColumn(name="profile_id"))
	private List<Trainings> trainings;
	@ElementCollection
	@CollectionTable(name="projects",joinColumns=@JoinColumn(name="profile_id"))
	private List<Projects> projects;
	@Embedded
	private Skills skills;
//	source collection table skills and list name skills are same 
	
	
	public int getId() {
		return id;
	}
	
	public List<Trainings> getTrainings() {
		return trainings;
	}

	public void setTrainings(List<Trainings> trainings) {
		this.trainings = trainings;
	}

	public Skills getSkills() {
		return skills;
	}

	public void setSkills(Skills skills) {
		this.skills = skills;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	
	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public List<Education> getEducation() {
		return education;
	}
	public void setEducation(List<Education> education) {
		this.education = education;
	}
	public List<Projects> getProjects() {
		return projects;
	}
	public void setProjects(List<Projects> projects) {
		this.projects = projects;
	}
	@Override
	public String toString() {
		return "Profile [id=" + id + ", name=" + name + ", title=" + title + ", about=" + about + ", contact=" + contact
				+ ", education=" + education + ", projects=" + projects + ", skills=" + skills + "]";
	}
	
	
	

}
