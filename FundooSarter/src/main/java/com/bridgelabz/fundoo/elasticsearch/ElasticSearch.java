package com.bridgelabz.fundoo.elasticsearch;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;

public interface ElasticSearch {

	public void deleteNote(Note note);
	public void createNote(Note note);
	public void updateNote(Note note);
	public void searchNote(Note note);
	
}
