package com.visionaryproviders.agridoctor.payloads;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FeedBackDto {

	
    private Integer feedBackId;
	
	private String feedBackTitle;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;	
	
	private UserDto user;
	
	
}
