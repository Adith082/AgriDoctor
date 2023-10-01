package com.visionaryproviders.agridoctor.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropDiseaseDetectionResponse {

	private String prediction;
	
	private String DiseaseNameEn, DiseaseNameBn, causeOfDiseaseEn, causeOfDiseaseBn, preventionOrCureEn, preventionOrCureBn, CropNameBn, CropNameEn;
	
	private String message;
	
	private int wallet;
	
	
	
}
