package com.notebook.iNotebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public ResponseEntity<?> signup(@RequestBody User user) throws Exception{
		if(user.getUsername()!=null) {
			if(user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()){
				String result = "Username, email or password cannot be empty";
				return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			}else {
				if (userDao.findByUsername(user.getUsername()) != null ) {
					String result = "Username, already register";
					return new ResponseEntity<>(result, HttpStatus.CONFLICT);
				}else {
					String encodedPassword = encoder.encode(user.getPassword());
					User myUser = new User(user.getUsername(), user.getEmail(), encodedPassword);
					userService.saveUser(myUser);
					String result = "Account created success";
					return new ResponseEntity<>(result, HttpStatus.CREATED);
				}
			}
        }
		else {
        	String result = "User already logged In";
        	return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
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
