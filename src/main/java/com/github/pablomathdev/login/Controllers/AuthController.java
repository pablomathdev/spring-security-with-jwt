package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Entities.User;
import com.github.pablomathdev.login.Repositories.UserRepository;
import com.github.pablomathdev.login.RepresentationModel.UserLoginModel;
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
	public ResponseEntity<?> signup(@RequestBody UserLoginModel loginModel) {

		var userPassword = new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword());

		var auth = this.authenticationManager.authenticate(userPassword);
		
		var token = jwtService.generateToken((User) auth.getPrincipal());

		return ResponseEntity.ok(token);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signin(@RequestBody User user) {

	  User alreadyExists = userRepository.findByEmail(user.getEmail());
	  
	  if(alreadyExists != null) {
		  return ResponseEntity.badRequest().build();
	  }
		
	  String encryptedpassword = new BCryptPasswordEncoder().encode(user.getPassword());
	  
	   user.setPassword(encryptedpassword); 
	   
	   userRepository.save(user);
	   
	   
	   return ResponseEntity.ok().build();
	  
	}
}
