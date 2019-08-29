package com.bridgelabz.fundoo.note.controler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoo.note.dto.LabelDTO;
import com.bridgelabz.fundoo.note.dto.NoteDTO;
import com.bridgelabz.fundoo.note.model.Label;
import com.bridgelabz.fundoo.note.model.Note;
import com.bridgelabz.fundoo.note.service.LabelService;
import com.bridgelabz.fundoo.response.Response;

@RestController
@RequestMapping("/Label")
@CrossOrigin(origins = "*",allowedHeaders = {"*"},exposedHeaders = {"emailID"})
public class LabelController {
	@Autowired
	private LabelService LabelService;

	/**
	 *  Purpose : Rest API For create new label
	 * @param labelDTO
	 * @param emailID
	 * @return
	 */
	@PostMapping("/Create")
	public ResponseEntity<Response> createNewLabel(@RequestBody LabelDTO labelDTO, @RequestHeader String emailID) {
		Response response = LabelService.createNewLabelForUser(labelDTO, emailID);
		return new ResponseEntity<Response>(response, HttpStatus.CREATED);
	}

	/**
	 *  Purpose : Rest API For Update existence label
	 * @param labelDTO
	 * @param emailID
	 * @param labelID
	 * @return
	 */
	@PutMapping("/Update")
	public ResponseEntity<Response> updateExistenceLabel(@RequestBody LabelDTO labelDTO, @RequestHeader String emailID,
			@RequestParam long labelID) {
		Response response = LabelService.updateExistenceLabel(labelDTO, emailID, labelID);
		return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
	}

	/**
	 *  Purpose : Rest API For delete user label
	 * @param emailID
	 * @param labelID
	 * @return
	 */
	@DeleteMapping("/Delete")
	public ResponseEntity<Response> deleteUserLabel(@RequestHeader String emailID, @RequestParam long labelID) {
		Response response = LabelService.deleteUserLabel(emailID, labelID);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 *  Purpose : Rest API For Getting all labels of user
	 * @param emailID
	 * @return
	 */
	@GetMapping("/GetLabels")
	List<Label> getAllLabels(@RequestHeader String emailID) {
		List<Label> listLabels = LabelService.getAllLabelsOfUser(emailID);
		return listLabels;
	}

	/**
	 *  Purpose : Rest API For Remove label from particular note
	 * @param labelID
	 * @param emailID
	 * @param noteID
	 * @return
	 */
	@GetMapping("/RemoveLabelFromNote")
	public ResponseEntity<Response> removeLabelFromNote(@RequestParam long labelID, @RequestHeader String emailID,
			@RequestParam long noteID) {
		Response response = LabelService.removeLabelFromNote(labelID, emailID, noteID);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	/**
	 *  Purpose : Rest API For Add label to particular note
	 * @param labelID
	 * @param emailID
	 * @param noteID
	 * @return
	 */
	@GetMapping("/addLabelToNote")
	public ResponseEntity<Response> addLabelToNote(@RequestParam long labelID, @RequestHeader String emailID,
			@RequestParam long noteID) {
		Response response = LabelService.addLabelToNote(labelID, emailID, noteID);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

	/**
	 *  Purpose : Rest API For Getting labels of particular note
	 * @param emailID
	 * @param noteID
	 * @return
	 */
	@GetMapping("/GetLabelsOfNote")
	List<Label> getLebelsOfNote(@RequestHeader String emailID, @RequestParam long noteID) {
		System.out.println("note labels");
		List<Label> listLabels = LabelService.getLabelsOfNote(emailID, noteID);
		//System.out.println(listLabels);
		return listLabels;
	}
	
	 /**
	  *  Purpose : Rest API For Getting notes of particular label
	 * @param emailID
	 * @param labelID
	 * @return
	 * @throws IllegalArgumentException
	 */
	@GetMapping("/GetNotesOfLabel")
	  List<Note> getNotesOfLabel(@RequestHeader String emailID,@RequestParam long labelID) throws IllegalArgumentException
	  {
		  List<Note>notesList=LabelService.getNotesOfLabel(emailID, labelID);
		  return notesList;
	  }

}
