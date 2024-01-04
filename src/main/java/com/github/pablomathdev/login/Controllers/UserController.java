package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Entities.User;
import com.github.pablomathdev.login.RepresentationModel.UserLoginModel;
import com.github.pablomathdev.login.Services.UserService;

@RestController
public class UserController {

	
	UserService userService;

	public UserController(@Autowired UserService userService) {
		this.userService = userService;
	}
	
	
	@PostMapping(value = "/users")
	private void registerUser(@RequestBody User user) {
		
		userService.createUser(user);
	}
	
	@GetMapping(value = "/users")
	private void signUp(@RequestBody UserLoginModel userLoginModel) {
		
		userService.loadUserByUsername(userLoginModel.getEmail());
		
	}
	
}
