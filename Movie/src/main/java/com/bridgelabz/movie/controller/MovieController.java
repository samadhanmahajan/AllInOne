package com.bridgelabz.movie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.movie.DTO.MovieDTO;
import com.bridgelabz.movie.model.Movie;
import com.bridgelabz.movie.responce.Response;
import com.bridgelabz.movie.service.MovieService;

@RestController
@RequestMapping("/movie")
public class MovieController {
	private MovieService movieService ;
	@GetMapping
	public List<Movie> getAllMovies(){
		return movieService.getAllMovies();		
	}

	@PostMapping
	public ResponseEntity<?> addMovie(@RequestHeader long[] arr,@RequestBody MovieDTO movieDTO){
		Response response = movieService.addMovie(movieDTO, arr);
		return new ResponseEntity<>(response,HttpStatus.OK);

	}
	
	@PutMapping
	public ResponseEntity<?> updateMovie(@RequestHeader long[] arr,@RequestBody MovieDTO movieDTO,@RequestParam long movieID){
		Response response = movieService.updateMovie(movieDTO, arr, movieID);
		return new ResponseEntity<>(response,HttpStatus.OK);

	}
}
