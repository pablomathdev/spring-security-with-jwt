package com.github.pablomathdev.login.infra.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.pablomathdev.login.Domain.Entities.Role;



public interface RoleRepository extends JpaRepository<Role,Long>{

	Optional<Role> findById(Long id);
	
}
