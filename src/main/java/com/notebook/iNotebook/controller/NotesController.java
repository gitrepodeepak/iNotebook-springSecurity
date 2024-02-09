package com.notebook.iNotebook.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.notebook.iNotebook.model.Notes;
import com.notebook.iNotebook.service.NotesService;

@org.springframework.stereotype.Controller
public class NotesController {
	@Autowired
	NotesService notesService;
	
	@GetMapping("/notes")
	public String getNotes(Model model, Principal principal) throws Exception {
		String username = principal.getName();
		List<String> myNotes =  notesService.loadNotes(username);
		model.addAttribute("myNotes", myNotes);
		return "notes.html";
	}
	
	@PostMapping("/addnote")
	public String addNote(@RequestParam String username, @RequestParam String note) throws Exception {
		notesService.addNotes(username, note);
		return "note added successful";
	}
	
}
