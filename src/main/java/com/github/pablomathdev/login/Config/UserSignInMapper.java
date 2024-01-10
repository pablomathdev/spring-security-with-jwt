package com.github.pablomathdev.login.Config;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.github.pablomathdev.login.Entities.User;
import com.github.pablomathdev.login.Models.UserSignInDTO;

@Component
@Mapper(componentModel = "spring")
public interface UserSignInMapper {


	
	@Mapping(source = "email",target = "username")
	@Mapping(source = "password",target = "password")
	@Mapping(target = "birthDate",ignore = true)
	@Mapping(target = "cpf",ignore = true)
	@Mapping(target = "rg",ignore = true)
	@Mapping(target = "phone",ignore = true)
	@Mapping(target = "firstName",ignore = true)
	@Mapping(target = "lastName",ignore = true)
	@Mapping(target = "roles",ignore = true)
	@Mapping(target = "authorities",ignore = true)
	@Mapping(target = "id",ignore = true)
	User userToUserSignInDto(UserSignInDTO user);
}
