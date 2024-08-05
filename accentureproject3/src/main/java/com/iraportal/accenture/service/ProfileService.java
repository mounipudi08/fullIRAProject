package com.iraportal.accenture.service;

//import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.iraportal.accenture.model.Profile;
import com.iraportal.accenture.repository.ProfileRepository;

@Service
public class ProfileService {
	@Autowired
	ProfileRepository profRepo;
	/**
	 * public List<Candidate> getAllCandidates() {
		List<Candidate> candidates = new ArrayList<Candidate>();
		candidateRepository.findAll().forEach(candidate -> candidates.add(candidate));
		return candidates;
	}
	 * @return
	 */
	public List<Profile> getAllProfiles() {
//		List<Profile> profiles = new ArrayList<Profile>();
//		profRepo.findAll().forEach(prof -> profiles.add(prof));
		return profRepo.findAll();
	}
	public void saveOrUpdate(Profile profile) {
		// TODO Auto-generated method stub
		profRepo.save(profile);
	}
	public Profile getProfileById(int id) {
		return profRepo.findById(id).orElse(null);
	}
	public Profile getProfileByName(String profileName) {
		// TODO Auto-generated method stub
		return profRepo.findByName(profileName);
	}
	
	public void deleteProfile(int profileid) {
			profRepo.deleteById(profileid);			
		
	}
	
	public Profile updateProfile(int profileid, Profile profileDetails) {
		Optional<Profile> profileOptional =profRepo.findById(profileid);
		if(profileOptional.isPresent()) {
			Profile profile=profileOptional.get();
			profile.setAvailability(profileDetails.getAvailability());
			return profRepo.save(profile);
		}else {
			return null;
		}
	}

}
