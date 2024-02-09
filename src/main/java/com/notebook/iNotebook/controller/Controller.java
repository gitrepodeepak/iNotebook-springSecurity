package com.notebook.iNotebook.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.iNotebook.dao.UserDao;
import com.notebook.iNotebook.model.MyUserDetails;
import com.notebook.iNotebook.model.User;
import com.notebook.iNotebook.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@org.springframework.stereotype.Controller
//@CrossOrigin
//@RequestMapping("/api")
public class Controller {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);

	
//	PrintWriter out = new PrintWriter(System.out); 
	
	@GetMapping("/")
	public String home(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("username", username);
		return "index.html";
	}
	
//	private AuthenticationManager authenticationManager;
//
//	public void LoginController(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//	}
//
//	@PostMapping("/login")
//	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
//		Authentication authenticationRequest =
//			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
//		Authentication authenticationResponse =
//			this.authenticationManager.authenticate(authenticationRequest);
//		// ...
//	}
//	
//	public record LoginRequest(String username, String password) {
//	}
	
	
	@GetMapping("/loginform")
	public String loginform() {
		return "login.html";
	}
	
//	@PostMapping("/login")
//	public String login(@RequestBody User user) throws Exception{
//		UserDetails myUser = userService.loadUserByUsername(user.getUsername());
//		if(encoder.matches(user.getPassword(), myUser.getPassword())) {
//			return "Successfully Logged In!";			
//		}else {
//			return "Password did not Match!";
//		}
//	}
	
//	@PostMapping("/login")
//	public String login(@RequestParam String username, @RequestParam String password, Model model) throws Exception{
//		UserDetails myUser = userService.loadUserByUsername(username);
//		if(encoder.matches(password, myUser.getPassword())) {
//			model.addAttribute("message", "Login Success");
//			return "result.html";			
//		}else {
//			model.addAttribute("message", "Password did not Match!");
//			return "result.html";
//		}
//	}
	
	@GetMapping("/signupform")
	public String signupForm() {
		return "signup.html";
	}
	
//	@PostMapping("/signup")
//	public String signup(@RequestBody User user) throws Exception{
//		System.out.println("Request Came" + user);
//		if(user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
//			return "<h1>username, email or password cannot be empty</h1>";
//		}else if(user.getPassword().length()<5){
//			return "<h1>Password cannot be less than 5 Characters</h1>";
//		}
//		else {			
//			if (userService.loadUserByUsername(user.getUsername()) != null ) {
//				throw new Exception("User already register");
//			}else {
//			String result = encoder.encode(user.getPassword());
//			user.setPassword(result);
//			userService.saveUser(user);	
//			return "<h1>Account Created Sucessful</h1>";
//			}
//		}
//	}
	
	@PostMapping("/signup")
	public String signup(@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password, Model model, 
			SecurityContextHolder securityContextHolder
			) throws Exception{
		
		
//		org.springframework.security.core.Authentication authentication = securityContextHolder.getContext().getAuthentication();
//		if (!(authentication instanceof AnonymousAuthenticationToken)) {
//		    String currentUserName = authentication.getName();
//		    // Use the username as needed
//			return "redirect:/home";
//		}

		
//		org.springframework.security.core.Authentication auth = securityContextHolder.getContext().getAuthentication();
//        if (auth.isAuthenticated()) {
//            // User is already logged in, redirect them to another page
//             // Redirect to home page or any other desired page
//        	return "redirect:/home";
//        }else {
        // If not logged in, allow access to the user creation page
//        return "createUser";
		if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
			model.addAttribute("message", "username, email or password cannot be empty");
			return "result.html";
		}else if(password.length()<5){
			model.addAttribute("message", "Password can't be less than 5 characters!");
			return "result.html";
		}
		else {
			
			if (userDao.findByUsername(username) != null ) {
				model.addAttribute("message", "username, already register");
				return "result.html";
			}else {
			String result = encoder.encode(password);
			User myUser = new User(username, email, result);
			userService.saveUser(myUser);	
			model.addAttribute("message", "account created success");
			return "result.html";
			}
		}
        }
//	}
	
	@GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // Invalidate the session
        }
        return "redirect:/login"; // Redirect to home page or any other desired page
    }
	
//	@GetMapping("/current-user")
//	public String getCurrentUser(Principal principal) {
////		out.println(principal.getName());
//		return principal.toString();
//	}
		
}
