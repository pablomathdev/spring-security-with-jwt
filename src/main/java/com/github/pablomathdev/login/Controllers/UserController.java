package com.github.pablomathdev.login.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pablomathdev.login.Domain.Entities.User;
import com.github.pablomathdev.login.Models.EmailDTO;
import com.github.pablomathdev.login.Models.UserEditDTO;
import com.github.pablomathdev.login.Services.EmailService;
import com.github.pablomathdev.login.Services.UserService;
import com.github.pablomathdev.login.infra.Mapper.UserUpdateMapper;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserUpdateMapper userUpdateMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JavaMailSenderImpl mailSender;

	@Autowired
	private EmailService emailService;

	@Autowired
	private UserService userService;



	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable Long id, @RequestBody UserEditDTO data) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		User getUserAuthContext = (User) authentication.getPrincipal();

		User findUser = userRepository.findById(getUserAuthContext.getId()).get();

		userUpdateMapper.updateUserFromUserEditDTO(data, findUser);

		userRepository.save(findUser);

		return ResponseEntity.ok().build();

	}

	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestBody EmailDTO email, HttpServletRequest request) {
		
		
		User user = userRepository.findByUsername(email.getEmail());
      
		
		if (user == null) {
			
			return ResponseEntity.notFound().build();
		}

		String token = UUID.randomUUID().toString();

		userService.createPasswordResetTokenForUser(user, token);
		
		mailSender.send(emailService.createResetPasswordEmail( token, user));

		
		return ResponseEntity.ok().build();
	  	
	}

}
