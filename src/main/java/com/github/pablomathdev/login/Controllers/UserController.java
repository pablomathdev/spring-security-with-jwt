package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.UserEditDTO;
import com.github.pablomathdev.login.infra.Mapper.UserUpdateMapper;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@RestController
@RequestMapping("user")
public class UserController {

	private UserUpdateMapper userUpdateMapper;
	
	private UserRepository userRepository;
	
	public UserController(
			@Autowired UserRepository userRepository,
			@Autowired UserUpdateMapper userUpdateMapper) {
		this.userRepository = userRepository;
		this.userUpdateMapper = userUpdateMapper;
	}





	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable Long id,@RequestBody UserEditDTO data) {
	
	 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	 
		
	 User getUserAuthContext = (User) authentication.getPrincipal();
	
	  User findUser = userRepository.findById(getUserAuthContext.getId()).get();
	 
	  userUpdateMapper.updateUserFromUserEditDTO(data, findUser);
	   
	  userRepository.save(findUser);
	  
	   return ResponseEntity.ok().build();
	  
	  
	   
	}
}
