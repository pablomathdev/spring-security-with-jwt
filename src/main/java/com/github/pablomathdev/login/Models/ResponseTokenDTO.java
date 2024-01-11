package com.github.pablomathdev.login.Models;

public class ResponseTokenDTO {

	private String token;

	public static Builder builder() {

		return new Builder();

	}

	public String getToken() {
		return token;
	}

	public static class Builder {

		private ResponseTokenDTO responseTokenDTO;

		private Builder() {
			responseTokenDTO = new ResponseTokenDTO();
		}

		public Builder token(String token) {

			responseTokenDTO.token = token;
			return this;
		}

		public ResponseTokenDTO build() {
			return responseTokenDTO;
		}
	}
}