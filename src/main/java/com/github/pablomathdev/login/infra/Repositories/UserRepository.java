package com.github.pablomathdev.login.infra.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Exceptions.UserAlreadyExistsException;


public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String email);
	
	Optional<User> findById(Long id);

	default User alreadyExists(String email) {

		User user = findByUsername(email);

		if (user != null) {
			throw new UserAlreadyExistsException("User already exists.");
		}

		return user;

	}
}
