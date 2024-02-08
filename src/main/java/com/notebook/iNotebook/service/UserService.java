package com.notebook.iNotebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.notebook.iNotebook.dao.UserDao;
import com.notebook.iNotebook.model.User;

@Service
public class UserService implements UserDetailsService{
	
	@Autowired
	private UserDao userDao;
	
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
	
	public List<User> findAllUser(){
		return userDao.findAll();
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userDao.findByUsername(username);
		
	}
	
	public User saveUser(User user) {
		return userDao.save(user);
	}
	
}
