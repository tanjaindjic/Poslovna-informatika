package com.poslovna.poslovna.security;

public class AuthenticationResponse {
	private String username;
	private String token;
	
	public AuthenticationResponse(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}
	
	public AuthenticationResponse() {
		super();
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
