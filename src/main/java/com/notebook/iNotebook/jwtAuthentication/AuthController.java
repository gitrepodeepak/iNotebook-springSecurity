package com.notebook.iNotebook.jwtAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;
    

//    private Logger logger = LoggerFactory.getLogger(AuthController.class);


    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest req, @RequestBody JwtRequest request) {
    	
        this.doAuthenticate(request.getUsername(), request.getPassword());
        
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        
        String token = this.helper.generateToken(userDetails);
        JwtResponse response = new JwtResponse(token, userDetails.getUsername());
        
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//        		new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
//		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
//    private Set<String> invalidatedTokens = new HashSet<>();
    
//    @PostMapping("/logout")
//    public ResponseEntity<?> logoutUser(@RequestHeader("Authorization") String token) {
//        String tokenValue = token.substring(7); // Remove "Bearer " prefix
//        invalidatedTokens.add(tokenValue);
//        return ResponseEntity.ok("Logout successful");
//    }


	private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}