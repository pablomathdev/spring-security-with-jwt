package com.github.pablomathdev.login;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.github.pablomathdev.login.Services.JwtService;

@SpringBootApplication
public class LoginWebApplication implements CommandLineRunner{


	
	
	public static void main(String[] args)  {
		SpringApplication.run(LoginWebApplication.class, args);
		
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		JwtService jwtService = new JwtService();
		
		com.github.pablomathdev.login.Entities.User user = new com.github.pablomathdev.login.Entities.User();
		
		user.setId(12L);
		user.setFirstName("Pablo");
		user.setLastName("Matheus");
		user.setEmail("pablomatheus171@gmail.com");
		
		
		System.out.println(jwtService.generateToken(user));
	 

		
//		System.out.println(jwtService.secret);

		
	}

}
