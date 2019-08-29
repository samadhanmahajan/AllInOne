package com.bridgeit.fundoo.elasticsearch;

import java.util.List;

import com.bridgeit.fundoo.note.model.Note;
import com.bridgeit.fundoo.response.Response;

public interface ElasticSearch {
	Response createNote(Note note);
	Response updateNote(Note note);
	Response deleteNote(long noteId);
    //Response searchNote(String title);
	//List<Note> searchData(String query, long userId);
	List<Note> searchData(String query, long userId);
	
}
