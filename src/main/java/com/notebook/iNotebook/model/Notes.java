package com.notebook.iNotebook.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

@Document(collection = "notes")
public class Notes {
	
//	@NonNull
//	@Indexed(unique=true)
//	private String id;

	@Id
	@NonNull
	@Indexed(unique=true)
	private String username;
	
	@NonNull
	private List<String> notes;
	
	public Notes(String username, List<String> notes) {
		super();
		this.username = username;
		this.notes = notes;
	}
	
//	public String getId() {
//		return id;
//	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getNotes() {
		return notes;
	}
	public void setNotes(List<String> notes) {
		this.notes = notes;
	}
}
