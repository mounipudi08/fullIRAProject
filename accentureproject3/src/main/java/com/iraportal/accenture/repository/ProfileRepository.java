package com.iraportal.accenture.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.iraportal.accenture.model.Profile;

public interface ProfileRepository extends JpaRepository<Profile,Integer> {

	Profile findByName(String profileName);

}
