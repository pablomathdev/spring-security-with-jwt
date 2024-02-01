package com.github.pablomathdev.login.Exceptions;

public class JwtSignatureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public JwtSignatureException(String message) {
		super(message);
	}

}
