package com.visionaryproviders.agridoctor.services;

import java.util.List;

import com.visionaryproviders.agridoctor.entities.FeedBack;
import com.visionaryproviders.agridoctor.payloads.FeedBackDto;

public interface FeedBackServices {

	
	//create 

		FeedBackDto createFeedBack(FeedBackDto feedBackDto,Integer userId);

		//update 

		FeedBackDto updateFeedback(FeedBackDto feedBackDto, Integer feedBackId);

		// delete

		void deleteFeedBack(Integer feedBackId);
		
		//get all feedbacks
		
		 List<FeedBackDto> getAllFeedBack();
		
		//get single feedback
		
		FeedBackDto getFeedBackById(Integer feedBackId);
		
		
		//get all feedbacks by user
		List<FeedBackDto> getFeedBacksByUser(Integer userId);
		
	
		
}
