package com.visionaryproviders.agridoctor.externalservices;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;

import com.visionaryproviders.agridoctor.payloads.CropRecommendationRequestDto;
import com.visionaryproviders.agridoctor.payloads.CropRecommendationResponse;
import com.visionaryproviders.agridoctor.payloads.UserDto;
import com.visionaryproviders.agridoctor.services.UserServices;

@CrossOrigin(origins = "https://therap-javafest-agridoctor.vercel.app")
@Service
public class CropRecommendationApiServices {
	@Value("${crop_recommendation.api.url}")
    private String cropRecommendationApiUrl;
	
	@Autowired
	private WeatherExternalApiServices weatherExternalApiServices;

	
	@Autowired
	private UserServices userServices;
	
	private final RestTemplate restTemplate;

    @Autowired
    public CropRecommendationApiServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CropRecommendationResponse callCropRecommendationApi(CropRecommendationRequestDto request) {
       
    	CropRecommendationResponse cropRecommendationResponse = new CropRecommendationResponse();
    	if(!userServices.subtractWalletUser(request.getUid())) {
    		    cropRecommendationResponse.setMessage("No currency left. Cannot Provide Service.");
    		    cropRecommendationResponse.setWallet(userServices.getWalletUser(request.getUid()));
    		    return cropRecommendationResponse;
    	}
    	
    	
    	
    	
    	
    	Map<String, Object> weatherData = weatherExternalApiServices.getWeatherData(request.getCity());
    	Double temperature = (Double) ((Map<String, Object>) weatherData.get("main")).get("temp");
    	Integer humidity = (Integer) ((Map<String, Object>) weatherData.get("main")).get("humidity");
    	
    	
    	
    	temperature = temperature - 273.15;
        temperature = Math.round(temperature * 100.0) / 100.0;
    	//fetched temperature and humidity succesfully
        
        request.setHumidity(humidity);
        request.setTemperature(temperature);
    	
    	
    	
    	
    	// Set request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the request body and headers
        HttpEntity<CropRecommendationRequestDto> entity = new HttpEntity<>(request, headers);

        // Make the POST request to the external API
        ResponseEntity<CropRecommendationResponse> responseEntity =
                restTemplate.postForEntity(cropRecommendationApiUrl, entity, CropRecommendationResponse.class);
        
        responseEntity.getBody().setMessage("Service Provided");
        responseEntity.getBody().setWallet(userServices.getWalletUser(request.getUid()));
        // Extract and return the response body
        return responseEntity.getBody();
    }
	
}
