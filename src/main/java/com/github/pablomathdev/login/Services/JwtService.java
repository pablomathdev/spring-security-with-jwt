package com.github.pablomathdev.login.Services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Entities.User;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class JwtService {


	@Value("${jwt.secret.key}")
	public String secret;
	
	 private SecretKey getSigningKey() {
		  byte[] keyBytes = secret.getBytes();
		  return Keys.hmacShaKeyFor(keyBytes);
		}
	 
	 
	public String validateToken(String token) {
		
		String jwt;
		
		try {
			 jwt = Jwts.parser()
						.verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getSubject();
				 
				return jwt;
			
		} catch (JwtValidationException e) {
			return "";
		}
		
		
	
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
				  .expiration(generateExpDate())
				  .signWith(getSigningKey(),Jwts.SIG.HS256)
				  .compact();
		 
		 return jwt;
		 
	}
	
	private Date generateExpDate() {
	  Instant exp = LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("Z"));
		
		return Date.from(exp);
	}
	
}
