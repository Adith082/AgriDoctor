package com.visionaryproviders.agridoctor.services.implementations;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionaryproviders.agridoctor.entities.FeedBack;
import com.visionaryproviders.agridoctor.entities.User;
import com.visionaryproviders.agridoctor.exceptions.ResourceNotFoundException;
import com.visionaryproviders.agridoctor.payloads.FeedBackDto;
import com.visionaryproviders.agridoctor.repositories.FeedBackRepository;
import com.visionaryproviders.agridoctor.repositories.UserRepository;
import com.visionaryproviders.agridoctor.services.FeedBackServices;

@Service
public class FeedBackServicesImplementation implements FeedBackServices {

	
	@Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;
	
	
	@Override
	public FeedBackDto createFeedBack(FeedBackDto feedBackDto, Integer userId) {
		
		 User user = this.userRepository.findById(userId)
	                .orElseThrow(() -> new ResourceNotFoundException("User ", "User id", userId));

	        FeedBack feedBack = this.modelMapper.map(feedBackDto, FeedBack.class);
	        feedBack.setImageName("default.png");
	        feedBack.setAddedDate(new Date());
	        feedBack.setUser(user);

	        FeedBack newFeedBack = this.feedBackRepository.save(feedBack);

	        return this.modelMapper.map(newFeedBack, FeedBackDto.class);
		
	}

	@Override
	public FeedBackDto updateFeedback(FeedBackDto feedBackDto, Integer feedBackId) {
		FeedBack feedBack = this.feedBackRepository.findById(feedBackId)
                .orElseThrow(() -> new ResourceNotFoundException("feedBack ", "feedBack id", feedBackId));

	    feedBack.setFeedBackTitle(feedBackDto.getFeedBackTitle());
        feedBack.setContent(feedBackDto.getContent());
        feedBack.setImageName(feedBackDto.getImageName());
		

        FeedBack updatedFeedBack = this.feedBackRepository.save(feedBack);
        return this.modelMapper.map(updatedFeedBack, FeedBackDto.class);
	}

	@Override
	public void deleteFeedBack(Integer feedBackId) {

        FeedBack feedBack = this.feedBackRepository.findById(feedBackId)
                .orElseThrow(() -> new ResourceNotFoundException("Post ", "post id", feedBackId));

        this.feedBackRepository.delete(feedBack);

	}

	@Override
	public List<FeedBackDto> getAllFeedBack() {
		
		List<FeedBack> allFeedBacks = this.feedBackRepository.findAll();
		
		List<FeedBackDto> allFeedBackDtos = allFeedBacks.stream().map((post) -> this.modelMapper.map(post, FeedBackDto.class))
                .collect(Collectors.toList());
		
		
		
		return allFeedBackDtos;
	}

	@Override
	public FeedBackDto getFeedBackById(Integer feedBackId) {
		
		
		FeedBack feedBack = this.feedBackRepository.findById(feedBackId)
                .orElseThrow(() -> new ResourceNotFoundException("feedback", "feedback id", feedBackId));
        return this.modelMapper.map(feedBack, FeedBackDto.class);
		
		
	}

	@Override
	public List<FeedBackDto> getFeedBacksByUser(Integer userId) {
		
		User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User ", "userId ", userId));
        List<FeedBack> feedBacks = this.feedBackRepository.findByUser(user);

        List<FeedBackDto> postDtos = feedBacks.stream().map((post) -> this.modelMapper.map(post, FeedBackDto.class))
                .collect(Collectors.toList());

        return postDtos;
        
	}

	@Override
	public List<FeedBackDto> searchFeedBacks(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
