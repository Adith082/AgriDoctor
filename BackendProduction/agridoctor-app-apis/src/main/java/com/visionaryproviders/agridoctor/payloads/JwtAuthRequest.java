package com.visionaryproviders.agridoctor.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	      
   private String username; // username is actually email here.
   private String password;
   
}
