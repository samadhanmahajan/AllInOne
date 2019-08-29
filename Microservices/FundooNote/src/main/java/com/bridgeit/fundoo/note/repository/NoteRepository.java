package com.bridgeit.fundoo.note.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgeit.fundoo.note.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long> {

	public Note findByNoteId(long noteId);
	public Note findByUserIdAndNoteId(long userId,long noteId);
	public List<Note> findByUserId(long UserId);
}
