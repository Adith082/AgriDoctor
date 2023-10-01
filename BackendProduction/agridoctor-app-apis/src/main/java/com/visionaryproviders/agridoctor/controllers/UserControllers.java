package com.visionaryproviders.agridoctor.controllers;

import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visionaryproviders.agridoctor.payloads.ApiResponse;
import com.visionaryproviders.agridoctor.payloads.UserDto;
import com.visionaryproviders.agridoctor.services.UserServices;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserControllers {
	@Autowired
	private UserServices userServices;
	
	// POST-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userServices.createUser(userDto);
		return new ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//PUT - update wallet by adding value
	@PutMapping("/{userId}/wallet-add/{addValue}")
	public ResponseEntity<UserDto> addUserWallet( @PathVariable("userId") Integer uid,@PathVariable("addValue") Integer addValue){
		UserDto updatedUser = this.userServices.addWalletUser(uid, addValue);
		return ResponseEntity.ok(updatedUser);
	}
	
	
	
	//PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		UserDto updatedUser = this.userServices.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	}
	
	// ADMIN
	//DELETE -delete user
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@RequestBody UserDto userDto, @PathVariable("userId") Integer uid){
		this.userServices.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK);
	}
	
	//GET - get all users
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userServices.getAllUsers());
	}
	
	//GET - get SingleUser using userId
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId){
		return ResponseEntity.ok(this.userServices.getUserById(userId));
	}
	
}
