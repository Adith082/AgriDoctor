package com.visionaryproviders.agridoctor.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visionaryproviders.agridoctor.exceptions.ResourceNotFoundException;
import com.visionaryproviders.agridoctor.entities.User;
import com.visionaryproviders.agridoctor.payloads.UserDto;
import com.visionaryproviders.agridoctor.services.UserServices;
import com.visionaryproviders.agridoctor.repositories.UserRepository;

@Service
public class UserServicesImplementation implements UserServices {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.UserDtoToUser(userDto);
		User savedUser  = this.userRepository.save(user);
		
		return this.UserToUserDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setWallet(userDto.getWallet());
		
		User updatedUser = this.userRepository.save(user);
		
		return this.UserToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		return this.UserToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List <User> users = this.userRepository.findAll();
		// below lambda steam api has been used
		List<UserDto> userDtos =  users.stream().map(user->this.UserToUserDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
	
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		this.userRepository.delete(user);
		
	}
    public User UserDtoToUser(UserDto userDto) {
    	User user = new User();
    	user.setId(userDto.getId());
    	user.setName(userDto.getName());
    	user.setEmail(userDto.getEmail());
    	user.setPassword(userDto.getPassword());
    	user.setWallet(userDto.getWallet());
    	return user;
    }
    
    public UserDto UserToUserDto(User user) {
    	UserDto userDto = new UserDto();
    	userDto.setId(user.getId());
    	userDto.setName(user.getName());
    	userDto.setEmail(user.getEmail());
    	userDto.setPassword(user.getPassword());
    	userDto.setWallet(user.getWallet());
    	return userDto;
    }
    
    
    
}
