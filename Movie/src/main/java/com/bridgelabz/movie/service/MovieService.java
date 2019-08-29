package com.bridgelabz.movie.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.movie.DTO.MovieDTO;
import com.bridgelabz.movie.model.Actor;
import com.bridgelabz.movie.model.Movie;
import com.bridgelabz.movie.model.Producer;
import com.bridgelabz.movie.repository.ActorRepository;
import com.bridgelabz.movie.repository.MovieRepository;
import com.bridgelabz.movie.repository.ProducerRepository;
import com.bridgelabz.movie.responce.Response;
import com.bridgelabz.movie.responce.ResponseHelper;

public class MovieService {
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ProducerRepository producerRepository;
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private ModelMapper mapper;
	
	public List<Movie> getAllMovies(){
		List<Movie> movies = movieRepository.findAll();
		return movies;
	}
	
	public Response addMovie(MovieDTO movieDTO,long[] arr) {
		Optional<Producer> producer = producerRepository.findById(movieDTO.getProducerID());
		if(!producer.isPresent()) {
			Response response = ResponseHelper.statusResponse(403, "invalid producer ID");
			return response;
		}
		Movie movie = mapper.map(movieDTO, Movie.class);
		//long actorID
		for(int i=0;i<arr.length;i++) {
			Optional<Actor> actor = actorRepository.findById(arr[i]);
			if(!actor.isPresent()) {
				Response response = ResponseHelper.statusResponse(403, "invalid actor ID");
				return response;
			}
			movie.getActors().add(actor.get());
		}
		producer.get().getMovies().add(movie);
		movieRepository.save(movie);
		producerRepository.save(producer.get());
		Response response = ResponseHelper.statusResponse(200, "movie added");
		return response;
	}
	
	public Response updateMovie(MovieDTO movieDTO,long[] arr,long movieID) {
		Optional<Movie> movie = movieRepository.findById(movieID);
		Optional<Producer> producer = producerRepository.findById(movieDTO.getProducerID());
		if(!producer.isPresent()) {
			Response response = ResponseHelper.statusResponse(403, "invalid producer ID");
			return response;
		}
		//Movie movie = mapper.map(movieDTO, Movie.class);
		//long actorID
		for(int i=0;i<arr.length;i++) {
			Optional<Actor> actor = actorRepository.findById(arr[i]);
			if(!actor.isPresent()) {
				Response response = ResponseHelper.statusResponse(403, "invalid actor ID");
				return response;
			}
			movie.get().getActors().add(actor.get());
		}
		producer.get().getMovies().add(movie.get());
		movieRepository.save(movie.get());
		producerRepository.save(producer.get());
		Response response = ResponseHelper.statusResponse(200, "movie updated");
		return response;
	}
}
