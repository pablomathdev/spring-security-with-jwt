package com.github.pablomathdev.login.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.pablomathdev.login.Entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	User findByUsername(String email);
}
