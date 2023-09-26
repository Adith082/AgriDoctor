package com.visionaryproviders.agridoctor.externalservices;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WeatherExternalApiServices {
	
	@Autowired
    private RestTemplate restTemplate;
	public Map<String, Object> getWeatherData(String city) {
        String apiKey = "9d7cde1f6d07ec55650544be1631307e"; // Replacing with the api key
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey;

        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Handle error
            return Collections.emptyMap();
        }
    }
}
