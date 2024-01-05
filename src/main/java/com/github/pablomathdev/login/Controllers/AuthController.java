package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Entities.User;
import com.github.pablomathdev.login.Models.ResponseTokenDTO;
import com.github.pablomathdev.login.Models.UserSignInDTO;
import com.github.pablomathdev.login.Models.UserSignUpDTO;
import com.github.pablomathdev.login.Repositories.UserRepository;
import com.github.pablomathdev.login.Services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private JwtService jwtService;

	@PostMapping("/signin")
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseTokenDTO signIn(@RequestBody UserSignUpDTO user) {

		var userPassword = new UsernamePasswordAuthenticationToken(
				user.getEmail(),
				user.getPassword());

		var auth = this.authenticationManager.authenticate(userPassword);
		
		var token = jwtService.generateToken((User) auth.getPrincipal());

		
		
		ResponseTokenDTO responseTokenDTO = new ResponseTokenDTO();
		responseTokenDTO.setToken(token);
		
		return responseTokenDTO;
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserSignInDTO user) {

	  User alreadyExists = userRepository.findByEmail(user.getEmail());
	  
	  if(alreadyExists != null) {
		  return ResponseEntity.badRequest().build();
	  }
		
	  String encryptedpassword = new BCryptPasswordEncoder()
			  .encode(user.getPassword());
	  
	   user.setPassword(encryptedpassword); 
	   
	   User newUser = new User();
	   newUser.setEmail(user.getEmail());
	   newUser.setPassword(user.getPassword());
	   
	   userRepository.save(newUser);
	   
	   
	   return ResponseEntity.ok().build();
	  
	}
}
