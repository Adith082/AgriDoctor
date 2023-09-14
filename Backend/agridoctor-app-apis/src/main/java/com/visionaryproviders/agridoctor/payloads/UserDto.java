package com.visionaryproviders.agridoctor.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    
	private int id;
	private String name, email, password;
	
}
