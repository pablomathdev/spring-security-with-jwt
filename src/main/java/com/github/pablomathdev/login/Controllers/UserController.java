package com.github.pablomathdev.login.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Services.JwtService;

@RestController
@RequestMapping("user")
public class UserController {

	private JwtService jwtService;
	
	
	
	public UserController(JwtService jwtService) {
		this.jwtService = jwtService;
	}



	@PutMapping("/{id}")
	public User edit(@PathVariable Long id,@RequestBody User data) {
		
	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
	 User getUser = (User) authentication.getPrincipal();
	 System.out.println(getUser.getId());
	 if(data.getId().equals(getUser.getId())) {
		 System.out.println("OK");
	 }
	   return data;
	}
}
