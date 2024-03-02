package com.github.pablomathdev.login.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.UserEditDTO;
import com.github.pablomathdev.login.Services.UserService;
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
	
//	@PostMapping("/resetPassword")
//	public ResponseEntity<?> resetPassword(@RequestParam String email){
//		
//		User user = userRepository.findByUsername(email);
//		
//		if(user == null) {
//			return ResponseEntity.notFound().build();
//		}
//		
//		String token = UUID.randomUUID().toString();
//		
//		
//		
//	}
//	https://www.baeldung.com/spring-security-registration-i-forgot-my-password
}
