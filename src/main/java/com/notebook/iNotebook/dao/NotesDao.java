package com.notebook.iNotebook.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import com.notebook.iNotebook.model.Notes;

public interface NotesDao extends MongoRepository<Notes, String>{
	 Notes findByUsername(String username);
	 
	 @Query("{ 'username' : ?0 }") 
	 @Update("{ $set: { 'notes' : ?1 } }") 
	 public int updateNotes(String username, List<String> notes);


}
