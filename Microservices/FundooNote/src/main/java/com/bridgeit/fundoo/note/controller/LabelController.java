
package com.bridgeit.fundoo.note.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
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

import com.bridgeit.fundoo.note.dto.LabelDTO;
import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.utility.TokenGenerator;
import com.bridgeit.fundoo.note.service.LabelService;

@RestController

@RequestMapping("/labelController")

@PropertySource("classpath:message.properties")

public class LabelController {

	@Autowired
	private LabelService labelService;

	@Autowired
	private TokenGenerator userToken;

	@PostMapping("/createLabel")
	public ResponseEntity<Response> createLabel(@RequestBody @Valid LabelDTO labeldto, @RequestHeader String emailId) {
		String token = labelService.getToken(emailId);
		long userId = userToken.decodeToken(token);
		Response response = labelService.createLabel(labeldto, userId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteLabel")
	public ResponseEntity<Response> deleteLabel(@RequestHeader String emailId, @RequestParam long labelId) {
		String token=labelService.getToken(emailId);
		long userId = userToken.decodeToken(token);
		Response response = labelService.deleteLabel(userId, labelId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
