package com.iraportal.accenture.Controller;

import java.util.List;

//import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


import com.iraportal.accenture.model.Profile;
import com.iraportal.accenture.service.ProfileService;

@RestController
public class ProfilesController {
	@Autowired
	ProfileService profServ;
	@GetMapping("/profiles")
	private List<Profile> getAllProfile(){
		return profServ.getAllProfiles();
	}
	// creating a get mapping that retrieves the detail of a specific candidate
		@GetMapping("/ProfileById/{profileId}")
		Profile getProfileById(@PathVariable("profileId") int profileid ) {
			return profServ.getProfileById(profileid);
			
		}
		//profileByName
		@GetMapping("/ProfileByName/{profileName}")
		Profile getProfileByName(@PathVariable("profileName") String profileName ) {
			return profServ.getProfileByName(profileName);
		}
	
	@PostMapping("/saveProfiles")
	Profile saveProfile(@RequestBody Profile profile) // raw data json
	{
		profServ.saveOrUpdate(profile);
		return profile;
	}
	
	@PutMapping("/updateProfile/{id}")
	public ResponseEntity<Profile> updateProfile(@PathVariable("id") int profileid,@RequestBody Profile profileDetails){
		Profile updatedProfile =profServ.updateProfile(profileid, profileDetails);
		if(updatedProfile !=null) {
			return ResponseEntity.ok(updatedProfile);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	@DeleteMapping("/deleteProfile/{id}")
	public ResponseEntity<Void> deleteProfile(@PathVariable("id") int profileid){
		Profile profile =profServ.getProfileById(profileid);
		if(profile!=null) {
			profServ.deleteProfile(profileid);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	
	
	
	
	
	
	
}
