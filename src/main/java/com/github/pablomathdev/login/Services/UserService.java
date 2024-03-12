package com.github.pablomathdev.login.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Domain.Entities.PasswordResetToken;
import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.infra.Repositories.PasswordResetTokenRepository;
import com.github.pablomathdev.login.infra.Repositories.RoleRepository;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository repository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

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

	public void createPasswordResetTokenForUser(User user, String token) {

		PasswordResetToken myToken = new PasswordResetToken();
		myToken.setUser(user);
		myToken.setToken(token);

		passwordResetTokenRepository.save(myToken);

	}

}
