package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.infra.Repositories.RoleRepository;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private RoleRepository roleRepository;
	private UserRepository repository;
	private BCryptPasswordEncoder passwordEncoder;

	public UserService(
			@Autowired RoleRepository roleRepository,
			@Autowired UserRepository repository,
			@Autowired BCryptPasswordEncoder passwordEncoder) {
		this.roleRepository = roleRepository;
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	public void createUser(User user) {
		
	  String encryptedPassword = passwordEncoder.encode(user.getPassword());
		
	   user.setPassword(encryptedPassword); 
	   user.addRole(roleRepository.findById(2L).get());
	   repository.save(user);
		
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
