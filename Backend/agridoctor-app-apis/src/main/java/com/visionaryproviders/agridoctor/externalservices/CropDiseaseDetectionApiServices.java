package com.visionaryproviders.agridoctor.externalservices;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;


import com.visionaryproviders.agridoctor.config.RestTemplateConfig;
import com.visionaryproviders.agridoctor.payloads.CropDiseaseDetectionRequestDto;
import com.visionaryproviders.agridoctor.payloads.CropDiseaseDetectionResponse;
import com.visionaryproviders.agridoctor.payloads.CropRecommendationResponse;

@Service
public class CropDiseaseDetectionApiServices {

	@Value("${crop_disease_Detection.api.url}")
    private String cropDiseaseDetectionApiUrl;
	
	
	
	//private RestTemplateConfig restTemplateConfig;

	
	@Autowired
    private RestTemplate restTemplate ;
	
	
	public CropDiseaseDetectionResponse callCropDiseaseDetectionApi(@RequestParam("input_data") MultipartFile file) throws IOException  {
		
		// Create headers for the multipart request
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
	   
		System.out.println("1111");
	 // Create a request entity with the file and headers
	    HttpEntity<byte[]> requestEntity = new HttpEntity<>(file.getBytes(), headers);
		//System.out.println(requestEntity.getHeaders());
	    System.out.println("2222");
	 // Make a POST request to the external API with the image file
	    
	 // Make the POST request to the external API
        ResponseEntity<CropDiseaseDetectionResponse> responseEntity =
                restTemplate.postForEntity(cropDiseaseDetectionApiUrl, requestEntity, CropDiseaseDetectionResponse.class);
	    
	    
	    
	/*    ResponseEntity<CropDiseaseDetectionResponse> responseEntity = restTemplate.exchange(
	        cropDiseaseDetectionApiUrl,
	        HttpMethod.POST,
	        requestEntity,
	        CropDiseaseDetectionResponse.class
	    ); */
	    System.out.println("3333333333333");
	    
		return responseEntity.getBody();
	}
}
