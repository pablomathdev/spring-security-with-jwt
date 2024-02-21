package com.github.pablomathdev.login.infra.Mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.UserEditDTO;

@Component
@Mapper(componentModel = "spring")
public interface UserUpdateMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "cpf", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "rg", ignore = true)
	@Mapping(target = "roles", ignore = true)
	@Mapping(source = "email", target = "username")
	@Mapping(target = "authorities", ignore = true)
	void updateUserFromUserEditDTO(UserEditDTO userEditDTO, @MappingTarget User user);

}
