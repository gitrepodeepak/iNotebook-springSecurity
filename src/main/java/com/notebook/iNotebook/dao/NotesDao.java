package com.notebook.iNotebook.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.notebook.iNotebook.model.Notes;

public interface NotesDao extends MongoRepository<Notes, String>{
	 Notes findByUsername(String username);
}
