package com.visionaryproviders.agridoctor.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropRecommendationRequestDto {
	
	private double nitrogen, phosphorous, pottasium, ph, rainfall, temperature, humidity;
	private String city;
	private int uid;
}
