package com.visionaryproviders.agridoctor.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    
	private int id;
	
	@NotEmpty
	@Size(min = 3, message="Username must be min of 3 char")
	private String name;
	
	@NotEmpty
	@Size(min = 5, max = 11, message = "Password must be minimum of 5 chars and maximum of 11 chars")
	//@Pattern(regexp = )
	private String password;
	
	@NotEmpty
	@Email(message = "Email address not valid.")
	private String email;
	
	@Min(value = 0, message = "Wallet value cannot be negative")
	private int wallet;
	
}
