package com.github.pablomathdev.login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.ResponseTokenDTO;
import com.github.pablomathdev.login.Models.UserSignInDTO;
import com.github.pablomathdev.login.Models.UserSignUpDTO;
import com.github.pablomathdev.login.Services.AuthUserService;
import com.github.pablomathdev.login.Services.UserService;
import com.github.pablomathdev.login.infra.Mapper.UserSignInMapper;
import com.github.pablomathdev.login.infra.Mapper.UserSignUpMapper;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private UserRepository userRepository;

	private AuthUserService authUserService;

	private UserSignInMapper userSignInMapper;

	private UserSignUpMapper userSignUpMapper;

	private UserService userService;

	public AuthController(
			
			@Autowired AuthUserService authUserService, @Autowired UserRepository userRepository,
			@Autowired UserSignInMapper userSignInMapper, @Autowired UserSignUpMapper userSignUpMapper,
			@Autowired UserService userService) {
		this.authUserService = authUserService;
		this.userRepository = userRepository;
		this.userSignInMapper = userSignInMapper;
		this.userSignUpMapper = userSignUpMapper;
		this.userService = userService;
	}

	@PostMapping("/signin")
	public ResponseTokenDTO signIn(@RequestBody UserSignInDTO userSignInDTO) {

		User dtoToUser = userSignInMapper.userToUserSignInDto(userSignInDTO);

		String token = authUserService.authenticateUser(dtoToUser);

		return ResponseTokenDTO.builder().token(token).build();

	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody UserSignUpDTO user) {

		userRepository.alreadyExists(user.getEmail());

		User dtoToUser = userSignUpMapper.userToUserSignUpDto(user);

		userService.createUser(dtoToUser);

		return ResponseEntity.ok().build();

	}
}
