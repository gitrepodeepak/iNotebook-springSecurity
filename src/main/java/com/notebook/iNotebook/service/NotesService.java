package com.notebook.iNotebook.service;

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
		if(notesDao.findByUsername(username)==null) {
//			throw new Exception("No Notes Found");
//			List<String> result = new ArrayList<String>();
			return null;
		}else {		
			Notes notes = notesDao.findByUsername(username);
			return notes.getNotes();
		}
	}
	
	public void addNotes(String username, List<String> notes) throws Exception{
		if(notesDao.findByUsername(username)==null) {
			Notes myNote = new Notes(username, notes);
			notesDao.save(myNote);
		}else {
			Notes myNotes = notesDao.findByUsername(username);
			List<String> myNewNotes = myNotes.getNotes();
			myNewNotes.addAll(notes);
			myNotes.setNotes(myNewNotes);
			notesDao.save(myNotes);			
		}
    }
	

	public void delNote(String username, String note) throws Exception{
		if(notesDao.findByUsername(username)==null) {
			throw new Exception("No Notes Found");
		}else {
			Notes myNotes = notesDao.findByUsername(username);
			List<String> myNotesList = myNotes.getNotes();
			if (!myNotesList.contains(note)) {
				throw new Exception("Notes Not Found");
		    }else {
		    	myNotesList.remove(note);
		    	notesDao.updateNotes(username, myNotesList);
	        }
		}
		}
	
}
