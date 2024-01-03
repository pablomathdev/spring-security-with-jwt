package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Entities.User;
import com.github.pablomathdev.login.Repositories.UserRepository;

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

	public void createUser(User user) {
		
	  String password =	passwordEncoder.encode(user.getPassword());
		
	   user.setPassword(password);
	  
		repository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = repository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("Email not found");
		}

		return user;
	}

}
