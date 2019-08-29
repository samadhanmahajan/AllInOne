package com.bridgelabz.fundoo.note.controler;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.NoteService;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.user.model.User;

import com.bridgelabz.fundoo.utility.TokenUtility;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


@RestController
@RequestMapping("/Note")
@PropertySource("classpath:ExceptinMessages.properties")
@CrossOrigin(origins = "*",allowedHeaders = {"*"},exposedHeaders = {"emailID"})
public class NoteController {

	@Autowired
	private NoteService noteService;
	
	@Autowired
	private TokenUtility tokenUtility;

	@GetMapping("/searchNote")
	public List<Note> searchNote(@RequestHeader String emailID,@RequestParam String key) throws ClassNotFoundException, JsonParseException, JsonMappingException, IOException
	{
		//long userID = tokenUtility.decodeToken(token);
		
		List<Note> notes = noteService.serchNote(key,emailID);	
		return notes;
		
	}
	
	/**
	 * Purpose : Rest API For create note
	 * @param noteDTO
	 * @param token
	 * @return
	 */
	@PostMapping("/Create")
	public ResponseEntity<Response> noteCreation(@RequestBody NoteDTO noteDTO, @RequestHeader String emailID) {
		System.out.println("NotesController.creatingNote()");
		Response responseStatus = noteService.createNewNoteForUser(noteDTO, emailID);
		return new ResponseEntity<Response>(responseStatus, HttpStatus.CREATED);
	}

	/**
	 * Purpose : Rest API For update existence note 
	 * @param noteDTO
	 * @param token
	 * @param noteId
	 * @return
	 */
	@PutMapping("/Update")
	public ResponseEntity<Response> updatingNote(@RequestBody NoteDTO noteDTO, @RequestHeader String emailID,
			@RequestParam long noteID) {

		Response responseStatus = noteService.userNoteUpdate(noteDTO, emailID, noteID);
		return new ResponseEntity<Response>(responseStatus, HttpStatus.ACCEPTED);
	}

	/**
	 * Purpose : Rest API For retrieve particular note 
	 * @param token
	 * @param noteID
	 * @return
	 */
	@PutMapping("/Retrieve")
	public ResponseEntity<Response> retrievingNote(@RequestHeader String emailID, @RequestParam long noteID) {

		Response responseStatus = noteService.userNoteRetrieve(emailID, noteID);
		return new ResponseEntity<Response>(responseStatus, HttpStatus.OK);
	}

	/**
	 * Purpose : Rest API For move note to trash
	 * @param token
	 * @param noteID
	 * @return
	 */
	@GetMapping("/NoteTrashuntrash")
	public ResponseEntity<Response> deletingNote(@RequestHeader String emailID, @RequestParam long noteID) {
		System.out.println("trsh un trash");
		Response responseStatus = noteService.noteAddToTrash(emailID, noteID);
		
		System.out.println(responseStatus);
		return new ResponseEntity<Response>(responseStatus, HttpStatus.OK);
	}

	@GetMapping("/TrashUntrsh")
	public ResponseEntity<Response> noteTrashUntrash(@RequestHeader String emailID, @RequestParam long noteID){
		Response responseStatus = noteService.trashUntrash(emailID,noteID);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
	}
	/**
	 *  Purpose : Rest API For Delete note permanently
	 * @param token
	 * @param noteId
	 * @return
	 */
	@GetMapping("/DeletePermanently")
	public ResponseEntity<Response> permenantdeletingNote(@RequestHeader String emailID, @RequestParam long noteId) {
		Response responsestatus = noteService.userNotedelete(emailID, noteId);
		return new ResponseEntity<Response>(responsestatus, HttpStatus.OK);

	}
	
	/**
	 * Purpose : Rest API For do note pined or unpined
	 * @param token
	 * @param noteID
	 * @return
	 */
	@PutMapping("/PinUnPin")
	public ResponseEntity<Response> pinnedOrNot(@RequestHeader String emailID ,@RequestParam long noteID)
	{
		Response responseStatus = noteService.pinUnpinNote(emailID, noteID);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);	
	}
	
	/**
	 * Purpose : Rest API For do note archived or unarchived  
	 * @param token
	 * @param noteID
	 * @return
	 */
	@GetMapping("/ArchievedUnarchived")
	public ResponseEntity<Response> archievedOrNot(@RequestHeader String emailID, @RequestParam long noteID)
	{
		Response responseStatus = noteService.archivedUnarchivedNote(emailID, noteID);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);	
	}
	
	/**
	 * Purpose : Rest API For set color to particular note
	 * @param token
	 * @param noteID
	 * @param color
	 * @return
	 */
	@PutMapping("/SetColour")
	public ResponseEntity<Response> colorSet(@RequestHeader String emailID, @RequestParam long noteID, @RequestParam String color)
	{
		Response responseStatus = noteService.setColourToNote(emailID, noteID, color);
		return new ResponseEntity<Response>(responseStatus,HttpStatus.OK);
		
	}
	
	/**
	 * Purpose : Rest API For get all Trashed notes
	 * @param token
	 * @return
	 */
	@GetMapping("/GetAllTrashedNotes")
	public List<Note> getTrashNotes(@RequestHeader String emailID) {
		List<Note> listnotes = noteService.getTrashedNotesOfUser(emailID);
		return listnotes;
	}
	

	/**
	 * Purpose : Rest API For get all pined notes
	 * @param token
	 * @return
	 */
	@GetMapping("/GetAllPinNotes")
	public List<Note> getPinnedNotes(@RequestHeader String emailID) {
		List<Note> listnotes = noteService.getPinnedNoteOfUser(emailID);
		return listnotes;
	}
	
	/**
	 * Purpose : Rest API For get all notes 
	 * @param token
	 * @return
	 */
	@GetMapping("/GetAllNotes")
	public List<Note> getAllNotes(@RequestHeader String emailID) {
		List<Note> listnotes = noteService.getAllNotesOfUser(emailID);
		return listnotes;
	}
	
	/**
	 * Purpose : Rest API For getting achieve note of user
	 * @param token
	 * @return List Of Archived Notes
	 */
	@GetMapping("/GetAllArchievedNotes")
	public List<Note> getArchieveNotes(@RequestHeader String emailID) {
		List<Note> listnotes = noteService.getArchievedNoteList(emailID);
		return listnotes;
	}
	
	/**
	 * Purpose : Rest API For find Note From User
	 * @param token
	 * @param title
	 * @param description
	 * @return Return a particular note
	 */
	@GetMapping("/findNote")
	public Note getNote(@RequestHeader String emailID, @RequestHeader String title, @RequestHeader String description)
	{
		Note note = noteService.findNoteFromUser(emailID, title, description);	
		return note;
		
	}
	
	/**
	 * Purpose : Rest API For set note Reminder
	 * @param token
	 * @param noteID
	 * @param time
	 * @return 
	 */
	@GetMapping("/SetRemainderToNote")
	public ResponseEntity<Response> setRemainderToNote(@RequestHeader String emailID ,@RequestParam long noteID,@RequestParam String time)
	{
		Response response = noteService.setReminderToNote(emailID, noteID, time);
		
		return new ResponseEntity<Response>(response,HttpStatus.OK);
		
	}
	
	/**
	 * Purpose : Rest API For delete note Reminder
	 * @param token
	 * @param noteID
	 * @return
	 */
	@GetMapping("/RemoveReminderFromNote")
	public ResponseEntity<Response> deleteRemainderToNote(@RequestHeader String emailID, @RequestParam long noteID)
	{System.out.println("buyyyyyy");
		Response response = noteService.closeReminder(emailID, noteID);
		return new ResponseEntity<Response>(response,HttpStatus.OK);
	}
	
	/**
	 * Purpose : Rest API For collaborate user to Note
	 * @param token
	 * @param emailID
	 * @param noteID
	 * @return
	 */
	@GetMapping("/CollaborateNote")
	public  ResponseEntity<Response> addCollab(@RequestHeader String emailID,@RequestParam String collaboratorEmail,@RequestParam long noteID) {
		Response responseStatus = noteService.collaborateNoteToUser(emailID, collaboratorEmail, noteID);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.CREATED);
	}
	/**
	 * Purpose : Rest API For remove collaborator
	 * @param token
	 * @param emailID
	 * @param noteID
	 * @return 
	 */
	@GetMapping("/CollaboratorRemoveFromNote")
	public ResponseEntity<Response> removeCollab(@RequestHeader String emailID,@RequestParam String collaboratorEmail,@RequestParam long noteID) {
		Response responseStatus = noteService.removeCollaboratorFromNote(emailID, collaboratorEmail, noteID);
		return new  ResponseEntity<Response> (responseStatus,HttpStatus.OK);
	}
	/**
	 * Purpose : Rest API For get all collaborated notes
	 * @param token
	 * @return
	 */
	@GetMapping("/GetAllCollaboratedNotes")
	public List<Note> getCollaboratedNotes(@RequestHeader String emailID) {
		List<Note> collaboratednotes = noteService.getAllCollaboratedNotes(emailID);
		return collaboratednotes;
	}
	
	/**
	 * Purpose : Rest API For get all collaborated notes
	 * @param token
	 * @return
	 */
	@GetMapping("/GetAllReminderNotes")
	public List<Note> getReminderNotes(@RequestHeader String emailID) {
		
		List<Note> collaboratednotes = noteService.getAllReminderNotes(emailID);
		return collaboratednotes;
	}
	
	/**
	 * Purpose : Rest API For getting all collaborated users
	 * @param token
	 * @param noteId
	 * @return
	 */
	@GetMapping("/GetAllCollaboratedUsers")
	public List<User> getCollaboratedUser(@RequestHeader String emailID , @RequestParam long noteID) {
		List<User> collaboratedUser = noteService.getAllCollaboratedUserOfNode(emailID, noteID);
		return collaboratedUser;
	}
	
	/**
	 * Purpose : Rest API For getting  sorted notes by title
	 * @param token
	 * @return
	 */
	@GetMapping("/GetSortedNotesByTitle")
	//@ApiOperation("mhgmhg")
	public List<Note> getSortedNotesByTitle(@RequestHeader String emailID) {
		List<Note> sortedNotes = noteService.getSortedNotesByTitle(emailID);
		return sortedNotes;
	}
	
	/**
	 * Purpose : Rest API For getting sorted notes by date
	 * @param token
	 * @return
	 */
	@GetMapping("/GetSortedNotesByDate")
	public List<Note> getSortedNotesByDate(@RequestHeader String emailID) {
		List<Note> sortedNotes = noteService.getSortedNotesByDate(emailID);
		return sortedNotes;
	}
	
}
