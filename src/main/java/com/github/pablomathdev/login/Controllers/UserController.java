package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Services.JwtService;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {

	
	
	private UserRepository userRepository;
	
	public UserController(@Autowired UserRepository userRepository) {
		this.userRepository = userRepository;
	}





	@PutMapping("/{id}")
	public User edit(@PathVariable Long id,@RequestBody User data) {
		
	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
	 User getUserAuthContext = (User) authentication.getPrincipal();
	
	  User findUser = userRepository.findById(getUserAuthContext.getId()).get();
	 
	    
	
	   return data;
	}
}
