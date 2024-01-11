package com.github.pablomathdev.login.Controllers;

import java.time.OffsetDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

	
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Object> handleBadCredentials(BadCredentialsException ex,WebRequest request) {
		
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
		problemDetail.setStatus(HttpStatus.UNAUTHORIZED.value());
		problemDetail.setDetail(ex.getMessage());
		problemDetail.setProperty("timestamp", OffsetDateTime.now());
		problemDetail.setProperty("message", "Invalid email or password");
		
		
		return new ResponseEntity<>(problemDetail,new HttpHeaders(),HttpStatus.UNAUTHORIZED);
		
	}
	
}
