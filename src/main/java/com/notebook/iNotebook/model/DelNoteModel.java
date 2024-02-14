package com.notebook.iNotebook.model;

public class DelNoteModel {
	private String username;
	private String note;
	
	@Override
	public String toString() {
		return "DelNoteModel [username=" + username + ", note=" + note + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
}
