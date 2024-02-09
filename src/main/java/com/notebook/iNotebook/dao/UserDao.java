package com.notebook.iNotebook.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.notebook.iNotebook.model.User;

public interface UserDao extends MongoRepository<User, String>{
	User findByUsername(String username);
}
