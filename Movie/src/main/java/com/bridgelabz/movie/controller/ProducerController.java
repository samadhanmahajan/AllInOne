package com.bridgelabz.movie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.movie.DTO.MovieDTO;
import com.bridgelabz.movie.DTO.ProducerDTO;
import com.bridgelabz.movie.responce.Response;
import com.bridgelabz.movie.service.ProducerService;

@RestController
@RequestMapping("/producer")
public class ProducerController {
	private ProducerService producerService;
	@PostMapping
	public ResponseEntity<?> addproducer(@RequestBody ProducerDTO producerDTO){
		Response response = producerService.addProducer(producerDTO);
		return new ResponseEntity<>(response,HttpStatus.OK);

	}
}
