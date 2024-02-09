package com.notebook.iNotebook.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//import com.notebook.iNotebook.model.LoginRequest;

@RestController
public class LoginController {
	private AuthenticationManager authenticationManager;
	
	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse = this.authenticationManager.authenticate(authenticationRequest);
		// ...
//		SecurityContextHolder.getContext().setAuthentication(authenticationResponse);
		
		HttpSession session = request.getSession(true);
		SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authenticationResponse);
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        
		return ResponseEntity.ok().build();
	}
	
	public record LoginRequest(String username, String password) {
		
	}
}
