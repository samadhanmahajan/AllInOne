package com.bridgeit.fundoo.note.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.bridgeit.fundoo.note.dto.NoteDTO;
import com.bridgeit.fundoo.note.model.Note;
import com.bridgeit.fundoo.note.service.NoteService;
import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.utility.TokenGenerator;

@RestController
@RequestMapping("/note")
@PropertySource("classpath:message.properties")
public class NoteController {

	@Autowired
	private TokenGenerator userToken;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Logger logger;

	@Autowired
	private NoteService noteService;

	@GetMapping
	public String test() {
		return "test apigateway";
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> creatNote(@Valid @RequestBody NoteDTO notedto, @RequestHeader String token) {
		
		logger.info(token);
		long userId = userToken.decodeToken(token);

		Response responseStatus = noteService.createNote(notedto, userId);
		logger.info("response message" + responseStatus.getStatusMessage());
		
		return new ResponseEntity<>(responseStatus , HttpStatus.OK);

	}

	@PutMapping("/update")
	public ResponseEntity<Response> updatingNote(@Valid @RequestBody NoteDTO noteDto, @RequestHeader String token,@RequestParam long noteId) {
		
		long userId = userToken.decodeToken(token);
		Response responseStatus = noteService.updateNote(noteDto, userId, noteId);
		return new ResponseEntity<>(responseStatus, HttpStatus.ACCEPTED);
	}

	@PutMapping("/retrieve")
	public ResponseEntity<Response> retrievingNote(@RequestHeader String token, @RequestParam long noteId) {
		long userId = userToken.decodeToken(token);
		Response responseStatus = noteService.retrieveNote(userId, noteId);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@PutMapping("/delete")
	public ResponseEntity<Response> deletingNote(@RequestHeader String token, @RequestParam long noteId) {

		long userId = userToken.decodeToken(token);
		Response responseStatus = noteService.deleteNote(userId, noteId);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@PutMapping("/deleteNotePermenantly")
	public ResponseEntity<Response> PermenantlydeleteNote(@RequestHeader String token, @RequestParam long noteId) {

		long userId = userToken.decodeToken(token);
		Response responseStatus = noteService.deleteNotePermenantly(userId, noteId);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@PutMapping("/pinned")
	public ResponseEntity<Response> pinnedOrNot(@RequestHeader String emailId, @RequestParam long noteId) {
		String token = noteService.getToken(emailId);
		long userId = userToken.decodeToken(token);
		Response responseStatus = noteService.checkPin(userId, noteId);
		return new ResponseEntity<Response>(responseStatus, HttpStatus.OK);
	}

	@PutMapping("/archived")
	public ResponseEntity<Response> archivedOrNot(@RequestHeader String token, @RequestParam long noteId) {
		
		long userId = userToken.decodeToken(token);
		Response responseStatus = noteService.archived(userId, noteId);
		return new ResponseEntity<>(responseStatus, HttpStatus.OK);
	}

	@GetMapping("/getAllNote")
	List<Note> getNote(@RequestHeader String token) {
		
		long userId = userToken.decodeToken(token);
		List<Note> listnote = noteService.getAllNote(userId);
		System.out.println(listnote);
		return listnote;
	}
	
	
	@PostMapping("/setReminder")
	public ResponseEntity<Response> setReminder(@RequestHeader String token, @RequestParam long noteId,
			@RequestParam String reminder) {

		long userId = userToken.decodeToken(token);
		Response status = noteService.setReminder(userId, noteId, reminder);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@GetMapping("/getParticularNoteReminder")
	LocalDateTime getNoteRemider(@RequestHeader String token, @RequestParam long noteId) {
		
		long userId = userToken.decodeToken(token);
		LocalDateTime reminder = noteService.getParticularNoteReminder(userId, noteId);
		return reminder;

	}
	

	@DeleteMapping("/deleteReminder")
	public ResponseEntity<Response> deleteReminder(@RequestHeader String token, @RequestParam long noteId) {
		long userId = userToken.decodeToken(token);
		Response status = noteService.deleteReminder(userId, noteId);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}


}
