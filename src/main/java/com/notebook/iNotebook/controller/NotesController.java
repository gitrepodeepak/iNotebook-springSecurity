package com.notebook.iNotebook.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.iNotebook.service.NotesService;

@RestController
@CrossOrigin
public class NotesController {
	
	@Autowired
	NotesService notesService;
	
	@GetMapping("/notes")
	public List<String> getNotes(Principal principal, Authentication authentication) throws Exception {
		String username = principal.getName();		
		if(username!=null) {
			List<String> myNotes =  notesService.loadNotes(username);
			if(myNotes==null) {
				throw new Exception("Notes not found");
			}else {
				return myNotes;							
			}
		}else {
			throw new Exception("Error founding notes!");
		}
	}
	
	@PostMapping("/addnote")
	public String addNote(Principal principal, @RequestParam List<String> notes) throws Exception {
		String username = principal.getName();		
		if(username!=null) {
			notesService.addNotes(username, notes);
//			res.setStatus(HttpServletResponse.SC_CREATED);
			return "Note Created successfully!";
		}else {
			throw new Exception("Error during adding note!");
		}
	}
	
	@PostMapping("/delnote")
	public String delNote(Principal principal, @RequestParam String note) throws Exception {
		String username = principal.getName();
		if(username!=null) {
			notesService.delNote(username, note);
//			res.setStatus(HttpServletResponse.SC_CREATED);
			return "Note deleted successfully!";
		}else {
			throw new Exception("Error during deleting note!");
		}
	}
	
}
