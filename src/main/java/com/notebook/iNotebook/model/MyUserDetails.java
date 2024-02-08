package com.notebook.iNotebook.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String email;
	public String password;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	
	public MyUserDetails(User user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	@Override
	public String getPassword() {
		return password;
	}

	public String getEmail() {
		return email;
	}
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
