package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.ResponseTokenDTO;
import com.github.pablomathdev.login.Models.UserSignInDTO;
import com.github.pablomathdev.login.Models.UserSignUpDTO;
import com.github.pablomathdev.login.Services.AuthUserService;
import com.github.pablomathdev.login.infra.Mapper.UserSignInMapper;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	private UserRepository userRepository;
	
	private AuthUserService authUserService;
	
	private UserSignInMapper userSignInMapper;


	public AuthController(
			@Autowired AuthUserService authUserService,
			@Autowired UserRepository userRepository,
			@Autowired UserSignInMapper userSignInMapper)
	{
		this.authUserService = authUserService;
		this.userRepository = userRepository;
		this.userSignInMapper = userSignInMapper;
	}
	
	

	@PostMapping("/signin")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseTokenDTO signIn(@RequestBody UserSignInDTO userSignInDTO) {

		User dtoToUser = userSignInMapper.userToUserSignInDto(userSignInDTO);
		
		String token = authUserService.authenticateUser(dtoToUser);

		
		ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO();
		responseTokenDTO.setToken(token);
		
		
		return responseTokenDTO;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserSignUpDTO user) {

	  User alreadyExists = userRepository.findByUsername(user.getEmail());
	  
	  if(alreadyExists != null) {
		  return ResponseEntity.badRequest().build();
	  }
		
	  String encryptedpassword = new BCryptPasswordEncoder()
			  .encode(user.getPassword());
	  
	   user.setPassword(encryptedpassword); 
	   
	   User newUser = new User();
	   newUser.setUsername(user.getEmail());
	   newUser.setPassword(user.getPassword());
	   newUser.setFirstName(user.getFirstName());
	   newUser.setLastName(user.getLastName());
	   newUser.setRoles(user.getRoles());   
	   userRepository.save(newUser);
	   
	   
	   return ResponseEntity.ok().build();
	  
	}
}
