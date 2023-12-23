package com.github.pablomathdev.login.Services;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {


	@Value("${jwt.secret.key}")
	public String secret;
	
	@Value("${jwt.duration}")
	public String duration;
	
	 private SecretKey getSigningKey() {
		  byte[] keyBytes = secret.getBytes();
		  return Keys.hmacShaKeyFor(keyBytes);
		}
	 
	 
	
	public String generateToken(User user) {
		
		 String jwt = Jwts.builder()
				.header()
				  .add("alg", "HS256")
				  .add("typ","JWT")
				.and()
				.subject(user.getId().toString())
				  .claim("name", String.format("%s %s",user.getFirstName(),user.getLastName()))
				  .claim("email",user.getEmail())
				  .signWith(getSigningKey(),Jwts.SIG.HS256)
				  .compact();
		 
		 return jwt;
		 
	}
	
	
}
