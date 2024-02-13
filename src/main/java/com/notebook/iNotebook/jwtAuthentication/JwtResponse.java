package com.notebook.iNotebook.jwtAuthentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtResponse {
	private String token;
	private String username;
	
	 public JwtResponse(String token, String username) {
	 	super();
	 	this.token = token;
	 	this.username = username;
	 }
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
