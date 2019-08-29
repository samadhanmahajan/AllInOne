package com.bridgelabz.movie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.movie.DTO.ActorDTO;
import com.bridgelabz.movie.DTO.MovieDTO;
import com.bridgelabz.movie.responce.Response;
import com.bridgelabz.movie.service.ActorService;

@RestController
@RequestMapping("/actor")
public class ActorController {
	private ActorService actorService;
	@PostMapping
	public ResponseEntity<?> addMovie(@RequestBody ActorDTO actorDTO){
		Response response = actorService.addActor(actorDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);

	}
}
