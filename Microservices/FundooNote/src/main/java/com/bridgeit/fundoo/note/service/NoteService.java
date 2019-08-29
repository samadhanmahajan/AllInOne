package com.bridgeit.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bridgeit.fundoo.note.dto.NoteDTO;
import com.bridgeit.fundoo.note.model.Note;
import com.bridgeit.fundoo.response.Response;

public interface NoteService {

	Response updateNote(NoteDTO noteDto, long userId, long noteId);

	Response retrieveNote(long userId, long noteId);

	Response setReminder(long userId, long noteId, String time);

	Response deleteReminder(long userId, long noteId);

	LocalDateTime getParticularNoteReminder(long userId, long noteId);
	
	Response deleteNote(long userId, long noteId);

	Response deleteNotePermenantly(long userId, long noteId);
	
	Response createNote(NoteDTO notedto, long userId);

	String getToken(String email);

	List<Note> getAllNote(long userId);

	Response checkPin(long userId, long noteId);

	Response archived(long userId, long noteId);

}
