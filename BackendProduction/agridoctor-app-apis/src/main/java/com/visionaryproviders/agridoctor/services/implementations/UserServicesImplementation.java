package com.visionaryproviders.agridoctor.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.visionaryproviders.agridoctor.exceptions.ResourceNotFoundException;
import com.visionaryproviders.agridoctor.config.AppConstants;
import com.visionaryproviders.agridoctor.entities.Role;
import com.visionaryproviders.agridoctor.entities.User;
import com.visionaryproviders.agridoctor.payloads.UserDto;
import com.visionaryproviders.agridoctor.services.UserServices;
import com.visionaryproviders.agridoctor.repositories.RoleRepository;
import com.visionaryproviders.agridoctor.repositories.UserRepository;

@Service
public class UserServicesImplementation implements UserServices {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	
	
	
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.UserDtoToUser(userDto);
		User savedUser  = this.userRepository.save(user);
		
		return this.UserToUserDto(savedUser);
	}

	
	@Override 
	public UserDto addWalletUser(Integer userId, Integer addValue) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		user.setWallet(user.getWallet() + addValue);
		User updatedUser = this.userRepository.save(user);
		return this.UserToUserDto(updatedUser);
	}
	
	
	@Override 
	public boolean subtractWalletUser(Integer userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		if(user.getWallet() > 0) {
			user.setWallet(user.getWallet() - 1);
			User updatedUser = this.userRepository.save(user);
			return true;
		}
		return false;
		 
	}
	
	@Override 
	public int getWalletUser(Integer userId) {
		
		User user = this.userRepository.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		return user.getWallet();
		 
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
    	User user = this.modelMapper.map(userDto, User.class);
    	
    	return user;
    }
    
    public UserDto UserToUserDto(User user) {
    	UserDto userDto = this.modelMapper.map(user, UserDto.class);
    
    	return userDto;
    }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();

		user.getRoles().add(role);

		User newUser = this.userRepository.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
	}
    
    
    
}
