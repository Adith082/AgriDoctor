package com.visionaryproviders.agridoctor.services;

import java.util.List;

import com.visionaryproviders.agridoctor.entities.User;
import com.visionaryproviders.agridoctor.payloads.UserDto;


public interface UserServices {
	
	UserDto registerNewUser(UserDto user);
    
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user, Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	void deleteUser(Integer userId);
	
}
