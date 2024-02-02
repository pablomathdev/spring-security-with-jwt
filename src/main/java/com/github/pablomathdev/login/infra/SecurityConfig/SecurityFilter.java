package com.github.pablomathdev.login.infra.SecurityConfig;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.pablomathdev.login.Services.JwtService;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	private JwtService jwtService;

	private UserRepository userRepository;

	public SecurityFilter(@Autowired JwtService jwtService, @Autowired UserRepository userRepository) {

		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			var token = this.recoveryToken(request);

			if (token != null) {
				String subject = jwtService.validateToken(token);

				UserDetails user = userRepository.findById(Long.valueOf(subject)).get();

				var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			
			

			filterChain.doFilter(request, response);
		} catch (JwtException ex) {
			
			ProblemDetail problemDetail = null;
			try {
				problemDetail = responseJwtException(ex,request);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.registerModule(new JavaTimeModule());
	        
	        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
	        
	        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
	        response.getWriter().flush();
	        response.getWriter().close();
		}

	}

	private String recoveryToken(HttpServletRequest request) {

		var authHeader = request.getHeader("Authorization");

		if (authHeader == null)
			return null;

		return authHeader.replace("Bearer", "");
	}
	
	private ProblemDetail responseJwtException(JwtException ex,HttpServletRequest request) throws URISyntaxException {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
		problemDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
		problemDetail.setDetail(ex.getMessage());
		problemDetail.setProperty("timestamp", OffsetDateTime.now());
		URI uri = new URI(request.getRequestURI());
		problemDetail.setInstance(uri);
		problemDetail.setProperty("message", "Token signature is invalid.");

	   return problemDetail;
	}

}
