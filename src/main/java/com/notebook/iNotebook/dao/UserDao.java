package com.notebook.iNotebook.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.notebook.iNotebook.model.User;

public interface UserDao extends MongoRepository<User, String>{
	User findByUsername(String username);
	
}
