package com.notebook.iNotebook.service;

import java.lang.reflect.Array;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notebook.iNotebook.dao.NotesDao;
import com.notebook.iNotebook.model.Notes;

@Service
public class NotesService {
	
	@Autowired
	NotesDao notesDao;
	
	public List<String> loadNotes(String username) throws Exception{
//		return notesDao.findByUsername(username);
		Notes notes = notesDao.findByUsername(username);
		
		if (notes.getUsername() != null) {
			return notes.getNotes();
			
		}else {
			throw new Exception("username is not found");
		}
	}
	
	public void addNotes(String username, String note) throws Exception{
		Notes notes = notesDao.findByUsername(username);
		
		if (notes.getUsername() != null) {
			List<String> myNotes = notes.getNotes();
			myNotes.add(note);
			notes.setNotes(myNotes);
			notesDao.save(notes);
		}else {
			throw new Exception("There is erro in adding note");
		}
    }
}
