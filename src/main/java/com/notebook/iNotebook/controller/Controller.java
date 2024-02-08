package com.notebook.iNotebook.controller;

//import java.security.Principal;
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

@RestController
@CrossOrigin
@RequestMapping("/api")
public class Controller {
	
	@Autowired
	private UserService userService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
	
	@GetMapping("/")
	public String home() {
		return "index.html";
	}
	
	@GetMapping("/all")
	public List<User> findAll(){
		System.out.println("Request Came");
		
		return userService.findAllUser();
	}
	
	@PutMapping("/login")
	public String login(@RequestBody User user) {
		UserDetails myUser = userService.loadUserByUsername(user.getUsername());
		if(encoder.matches(user.getPassword(), myUser.getPassword())) {
			return "Successfully Logged In!";			
		}else {
			return "Password did not Match!";
		}
	}
	
	
	@PutMapping("/save")
	public String save(@RequestBody User user) throws Exception{
		System.out.println("Request Came" + user);
		if(user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
			return "username, email or password cannot be empty";
		}else if(user.getPassword().length()<5){
			return "Password cannot be less than 5 Characters";
		}
		else {		
			String result = encoder.encode(user.getPassword());
			user.setPassword(result);
			userService.saveUser(user);	
			return "Account Created Sucessful";
		}
	}
	
	
//	@GetMapping("/current-user")
//	public String getCurrentUser(Principal principal) {
//		return principal.getName();
//	}
		
}
