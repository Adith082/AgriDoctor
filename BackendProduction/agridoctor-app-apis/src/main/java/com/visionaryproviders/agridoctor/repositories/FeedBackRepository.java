package com.visionaryproviders.agridoctor.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.visionaryproviders.agridoctor.entities.FeedBack;
import com.visionaryproviders.agridoctor.entities.User;

public interface FeedBackRepository extends JpaRepository<FeedBack, Integer>{
	
	List<FeedBack> findByUser(User user);
}
