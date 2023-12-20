package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Entities.User;
import com.github.pablomathdev.login.Repositories.UserRepository;

@Service
public class UserService  implements UserDetailsService{

	private UserRepository repository;
	
	
	
	public UserService(@Autowired UserRepository repository) {
		super();
		this.repository = repository;
	}



	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Email not found");
		}
		
		return user;
	}


	
	
}
