package com.notebook.iNotebook.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.iNotebook.model.DelNoteModel;
import com.notebook.iNotebook.model.Notes;
import com.notebook.iNotebook.service.NotesService;

@RestController
//@CrossOrigin
public class NotesController {
	
	@Autowired
	NotesService notesService;
	
	@GetMapping("/notes")
	public ResponseEntity<List<String>> getNotes(Principal principal, Authentication authentication) throws Exception {
		String username = principal.getName();		
		if(username!=null) {
			List<String> myNotes =  notesService.loadNotes(username);
			if(myNotes==null) {
//				throw new Exception("Notes not found");
//				String result = "No notes found";
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>(myNotes, HttpStatus.OK);							
			}
		}else {
			throw new Exception("Error founding notes!");
		}
	}
	
	@PostMapping("/addnote")
	public String addNote(@RequestBody Notes notes) throws Exception {	
		String username = notes.getUsername();
		List<String> notesList = notes.getNotes();
		if(username!=null) {
			notesService.addNotes(username, notesList);
			return "Note Created successfully!";
		}else {
			throw new Exception("Error during adding note!");
		}
	}
	
//	@PostMapping("/delnote")
//	public String delNote(@RequestParam String note, @RequestParam String username) throws Exception {
//		if(username!=null) {
//			notesService.delNote(username, note);
////			res.setStatus(HttpServletResponse.SC_CREATED);
//			return "Note deleted successfully!";
//		}else {
//			throw new Exception("Error during deleting note!");
//		}
//	}
	@PostMapping("/delnote")
	public String delNote(@RequestBody DelNoteModel note) throws Exception {
		String username = note.getUsername();
		String delnote = note.getNote();
		if(username!=null) {
			notesService.delNote(username, delnote);
			return "Note deleted successfully!";
		}else {
			throw new Exception("Error during deleting note!");
		}
	}
	
}
