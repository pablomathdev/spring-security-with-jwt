package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private UserRepository repository;
	
	private BCryptPasswordEncoder passwordEncoder;

	public UserService(
			@Autowired UserRepository repository,
			@Autowired BCryptPasswordEncoder passwordEncoder) {
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public Boolean createUser(User user) {
		
	  String encryptedPassword = passwordEncoder.encode(user.getPassword());
		
	   user.setPassword(encryptedPassword);
	   
	   repository.save(user);
		
	   return true;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = repository.findByUsername(email);

		if (user == null) {
			throw new UsernameNotFoundException("Email not found");
		}

		return user;
	}

}
