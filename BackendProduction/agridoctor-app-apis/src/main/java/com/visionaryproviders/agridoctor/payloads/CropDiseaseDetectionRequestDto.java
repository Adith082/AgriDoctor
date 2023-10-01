package com.visionaryproviders.agridoctor.payloads;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropDiseaseDetectionRequestDto {
	
    private int uid;
    
}
