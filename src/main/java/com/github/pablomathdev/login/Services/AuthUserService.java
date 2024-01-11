package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Domain.Entities.User;


@Service
public class AuthUserService {
	
	
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@Autowired
	private JwtService jwtService;
	
	
	public String authenticateUser(User user) {
		
		
		
		Authentication userPassword = new UsernamePasswordAuthenticationToken(
				user.getUsername(),
				user.getPassword());

	
		try {
			var auth = this.authenticationManager.authenticate(userPassword);
			 
			
			
			var token = jwtService.generateToken((User) auth.getPrincipal());
			return token;
		} catch (AuthenticationException e) {
			
			throw new BadCredentialsException(e.getMessage(),e.getCause());
			
		}
		

		
		
		
	}
}
