package com.visionaryproviders.agridoctor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visionaryproviders.agridoctor.payloads.ApiResponse;
import com.visionaryproviders.agridoctor.payloads.FeedBackDto;
import com.visionaryproviders.agridoctor.services.FeedBackServices;
import java.util.List;
@RestController
@RequestMapping("/api/")
public class FeedBackControllers {
	
	@Autowired
	private FeedBackServices feedBackService;
	
	//create feedback
	
	@PostMapping("/user/{userId}/feedback")
	public ResponseEntity<FeedBackDto> createPost(@RequestBody FeedBackDto feedBackDto, @PathVariable Integer userId) {
		FeedBackDto createFeedBack = this.feedBackService.createFeedBack(feedBackDto, userId);
		return new ResponseEntity<FeedBackDto>(createFeedBack, HttpStatus.CREATED);
	}
	
	
	// get all feedbacks by user

		@GetMapping("/user/{userId}/feedbacks")
		public ResponseEntity<List<FeedBackDto>> getPostsByUser(@PathVariable Integer userId) {

			List<FeedBackDto> posts = this.feedBackService.getFeedBacksByUser(userId);
			return new ResponseEntity<List<FeedBackDto>>(posts, HttpStatus.OK);

		}
		
		
		
		// get all feedbacks

		@GetMapping("/feedbacks")
		public ResponseEntity <List<FeedBackDto>> getAllFeedBack(){
			List <FeedBackDto> allFeedBack = this.feedBackService.getAllFeedBack();
			return new ResponseEntity<List<FeedBackDto>>(allFeedBack,HttpStatus.OK);
		}
		
		
		
		// get feedBack details by feedBackid

		@GetMapping("/feedbacks/{feedBackId}")
		public ResponseEntity<FeedBackDto> getFeedBackById(@PathVariable Integer feedBackId) {

			FeedBackDto feedBackDto = this.feedBackService.getFeedBackById(feedBackId);
			return new ResponseEntity<FeedBackDto>(feedBackDto, HttpStatus.OK);

		}

	
		
		// delete feedBack
		@DeleteMapping("/feedbacks/{feedBackId}")
		public ApiResponse deleteFeedBack(@PathVariable Integer feedBackId) {
			this.feedBackService.deleteFeedBack(feedBackId);
			return new ApiResponse("FeedBack is successfully deleted !!", true);
		}

		// update post

		@PutMapping("/feedbacks/{feedBackId}")
		public ResponseEntity<FeedBackDto> updatePost(@RequestBody FeedBackDto feedBackDto, @PathVariable Integer feedBackId) {

			FeedBackDto updateFeedBack = this.feedBackService.updateFeedback(feedBackDto, feedBackId);
			return new ResponseEntity<FeedBackDto>(updateFeedBack, HttpStatus.OK);

		}
		
		
		
		
		
		
		
		

}