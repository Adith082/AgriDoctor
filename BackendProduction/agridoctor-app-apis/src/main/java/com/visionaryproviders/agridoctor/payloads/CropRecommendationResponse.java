package com.visionaryproviders.agridoctor.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropRecommendationResponse {
	
	private String recommendation;
	private String message;
	int wallet;
}
