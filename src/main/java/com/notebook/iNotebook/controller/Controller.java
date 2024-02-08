package com.notebook.iNotebook.controller;

import java.io.PrintWriter;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.iNotebook.model.User;
import com.notebook.iNotebook.service.UserService;

@org.springframework.stereotype.Controller
//@CrossOrigin
//@RequestMapping("/api")
public class Controller {
	
	@Autowired
	private UserService userService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
	
	PrintWriter out = new PrintWriter(System.out); 
	
	@GetMapping("/")
	public String home() {
		return "index.html";
	}
	
	@PostMapping("/all")
	public List<User> findAll(){
		System.out.println("Request Came");
		
		return userService.findAllUser();
	}
	
	@GetMapping("/loginform")
	public String loginForm() {
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
	
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password) throws Exception{
		UserDetails myUser = userService.loadUserByUsername(username);
		if(encoder.matches(password, myUser.getPassword())) {
			return "index.html";			
		}else {
			return "Password did not Match!";
		}
	}
	
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
	public String signup(@RequestParam String username, @RequestParam String email, @RequestParam String password) throws Exception{
		if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
			return "<h1>username, email or password cannot be empty</h1>";
		}else if(password.length()<5){
			return "<h1>Password cannot be less than 5 Characters</h1>";
		}
		else {			
			if (userService.loadUserByUsername(username) != null ) {
				throw new Exception("User already register");
			}else {
			String result = encoder.encode(password);
			User myUser = new User(username, email, password);
			userService.saveUser(myUser);	
			return "<h1>Account Created Sucessful</h1>";
			}
		}
	}
	
	@GetMapping("/current-user")
	public String getCurrentUser(Principal principal) {
		out.println(principal.getName());
		return principal.toString();
	}
		
}
