package com.iraportal.accenture.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;

//import java.util.List;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
@Embeddable
public class Skills {
	@Column(name="primary_skills")
	private String primary;
	@Column(name="secondary_skills")
	private List<String> secondary;
	
	public String getPrimary() {
		return primary;
	}
	public void setPrimary(String primary) {
		this.primary = primary;
	}
	public List<String> getSecondary() {
		return secondary;
	}
	public void setSecondary(List<String> secondary) {
		this.secondary = secondary;
	}
	@Override
	public String toString() {
		return "Skills [primary=" + primary + ", secondary=" + secondary + "]";
	}
	
	
}
