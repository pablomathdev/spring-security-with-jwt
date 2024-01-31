package com.github.pablomathdev.login.infra.SecurityConfig;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.pablomathdev.login.Services.JwtService;
import com.github.pablomathdev.login.infra.Repositories.UserRepository;

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
		} catch (Exception e) {
			
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setCharacterEncoding("UTF-8");
			response.getWriter().println(e.getMessage());
		}

	}

	private String recoveryToken(HttpServletRequest request) {

		var authHeader = request.getHeader("Authorization");

		if (authHeader == null)
			return null;

		return authHeader.replace("Bearer", "");
	}

}
