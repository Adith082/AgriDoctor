package com.visionaryproviders.agridoctor.externalservices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;


import com.visionaryproviders.agridoctor.config.RestTemplateConfig;
import com.visionaryproviders.agridoctor.payloads.CropDiseaseDetectionRequestDto;
import com.visionaryproviders.agridoctor.payloads.CropDiseaseDetectionResponse;

@Service
public class CropDiseaseDetectionApiServices {

	@Value("${crop_disease_Detection.api.url}")
    private String cropDiseaseDetectionApiUrl;
	
	
	
	private RestTemplateConfig restTemplateConfig;

	
	
	
	
	public CropDiseaseDetectionResponse callCropDiseaseDetectionApi(@RequestPart("input_data") CropDiseaseDetectionRequestDto request)  {
		
		// Create headers for the multipart request
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	   
		
	 // Create a request entity with the file and headers
	    HttpEntity<byte[]> requestEntity = null;
		try {
			requestEntity = new HttpEntity<>(request.getInputData().getBytes(), headers);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	 // Make a POST request to the external API with the image file
	    ResponseEntity<CropDiseaseDetectionResponse> responseEntity = restTemplateConfig.restTemplate().exchange(
	        cropDiseaseDetectionApiUrl,
	        HttpMethod.POST,
	        requestEntity,
	        CropDiseaseDetectionResponse.class
	    );
	    
	    
		return responseEntity.getBody();
	}
}
