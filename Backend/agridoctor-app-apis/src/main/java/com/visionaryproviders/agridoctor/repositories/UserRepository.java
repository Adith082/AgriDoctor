package com.visionaryproviders.agridoctor.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visionaryproviders.agridoctor.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByEmail(String email);
}
