package com.github.pablomathdev.login.infra.SecurityConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private SecurityFilter securityFilter;

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		                http
		                .authorizeHttpRequests(authorize -> authorize
						.requestMatchers(HttpMethod.POST, "/user/resetPassword").permitAll()
						.requestMatchers(HttpMethod.PUT, "/user/{id}").authenticated()
						.requestMatchers(HttpMethod.POST, "/auth/signin").permitAll()
						.requestMatchers(HttpMethod.POST, "/auth/signup").permitAll().requestMatchers("/h2-console/**")
						.permitAll()
						)
		                .csrf(csrf -> csrf.disable())
						.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()))
				.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}

}
