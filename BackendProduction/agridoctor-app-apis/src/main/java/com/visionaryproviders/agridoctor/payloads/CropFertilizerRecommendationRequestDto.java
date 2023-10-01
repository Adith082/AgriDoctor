package com.visionaryproviders.agridoctor.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropFertilizerRecommendationRequestDto {
	
	
	private int nitrogen, phosphorous, pottasium ;
	
	private String cropName;
	
	private int uid;
}
