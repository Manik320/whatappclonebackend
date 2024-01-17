package com.rubby.request;

import lombok.Data;

@Data
public class LoginRequest {

	private String email;
	private String Password;
	
	public  LoginRequest(String email, String password) {

		this.email = email;
		Password = password;
	}
	
	
}
