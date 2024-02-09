package com.notebook.iNotebook.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.iNotebook.dao.UserDao;
import com.notebook.iNotebook.model.User;
import com.notebook.iNotebook.service.UserService;

@RestController
//@CrossOrigin
//@RequestMapping("/api")
public class Controller {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserService userService;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16); 
	
//	@GetMapping("/")
//	public String home(Principal principal, Model model) {
//		String username = principal.getName();
//		model.addAttribute("username", username);
//		return "index.html";
//	}
	
	
//	@GetMapping("/loginform")
//	public String loginform() {
//		return "login.html";
//	}
//	
//	
//	@GetMapping("/signupform")
//	public String signupForm() {
//		return "signup.html";
//	}
	
	
	@PostMapping("/signup")
	public String signup(@RequestParam String username,
			@RequestParam String email,
			@RequestParam String password, 
			Principal principal) throws Exception{
		if(principal.getName()==null) {
			if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
				return "Username, email or password cannot be empty";
			}else if(password.length()<5){
				return "Password can't be less than 5 characters!";
			}else {
				if (userDao.findByUsername(username) != null ) {
					return "Username, already register";
				}else {
					String result = encoder.encode(password);
					User myUser = new User(username, email, result);
					userService.saveUser(myUser);
					return "Account created success";
				}
			}
        }else {
        	return "User already logged In";
        }
	}
	
//	@GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.invalidate();
//        }
//        return "redirect:/login";
//    }
		
}
