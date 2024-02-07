package com.notebook.iNotebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.notebook.iNotebook.dao.UserDao;
import com.notebook.iNotebook.model.User;

@Service
//public class UserService implements UserDetailsService{
public class UserService{
	
	
	@Autowired
	private UserDao userDao;
	
//	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
	
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		return userDao.findByUsername(username);
//		
//	}
	
	public List<User> findAllUser(){
		return userDao.findAll();
	}
	
	public User saveUser(User user) {
//		String result = encoder.encode(user.getPassword());
//		user.setPassword(result);
		return userDao.save(user);
	}
	
}
