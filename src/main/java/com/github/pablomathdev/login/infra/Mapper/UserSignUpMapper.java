package com.github.pablomathdev.login.infra.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.UserSignUpDTO;

@Component
@Mapper(componentModel = "spring")
public interface UserSignUpMapper {

	@Mapping(source = "email", target = "username")
	@Mapping(source = "password", target = "password")
	@Mapping(source= "firstName", target = "firstName")
	@Mapping(source = "lastName",target = "lastName")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "birthDate",ignore = true)
	@Mapping(source= "cpf",target = "cpf")
	@Mapping(target = "phone",ignore = true)
	@Mapping(target = "authorities",ignore = true)
	@Mapping(target = "rg",ignore = true)
	User userSignUpDtoToUser(UserSignUpDTO user);
}
