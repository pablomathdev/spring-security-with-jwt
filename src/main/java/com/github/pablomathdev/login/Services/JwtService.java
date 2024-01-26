package com.github.pablomathdev.login.Services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

import com.github.pablomathdev.login.Domain.Entities.User;

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

		try {
			return Jwts.parser()
					.verifyWith(getSigningKey())
					.build()
					.parseSignedClaims(token.replace(" ", ""))
					.getPayload()
					.getSubject();

			
			
		} catch (JwtValidationException e) {
			return "";
		}

	}

	public String generateToken(User user) {

		String jwt = Jwts.builder().header().add("alg", "HS256").add("typ", "JWT").and().issuer("api")
				.subject(user.getId().toString())
				.claim("name", String.format("%s %s", user.getFirstName(), user.getLastName()))
				.claim("email", user.getUsername())
				.expiration(generateExpDate())
				.issuedAt(Date.from(Instant.now()))
				.signWith(getSigningKey(), Jwts.SIG.HS256).compact();

		return jwt;

	}

	private Date generateExpDate() {
		 Instant exp = Instant.now().plus(2, ChronoUnit.HOURS);
	        return Date.from(exp);
	}

}
