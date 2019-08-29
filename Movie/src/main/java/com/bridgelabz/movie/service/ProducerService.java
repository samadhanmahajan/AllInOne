package com.bridgelabz.movie.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.movie.DTO.ActorDTO;
import com.bridgelabz.movie.DTO.ProducerDTO;
import com.bridgelabz.movie.model.Actor;
import com.bridgelabz.movie.model.Producer;
import com.bridgelabz.movie.repository.ActorRepository;
import com.bridgelabz.movie.repository.MovieRepository;
import com.bridgelabz.movie.repository.ProducerRepository;
import com.bridgelabz.movie.responce.Response;
import com.bridgelabz.movie.responce.ResponseHelper;

public class ProducerService {

	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ProducerRepository producerRepository;
	@Autowired
	private ActorRepository actorRepository;
	@Autowired
	private ModelMapper mapper;
	public Response addProducer(ProducerDTO producerDTO) {
		Producer producer = mapper.map(producerDTO, Producer.class);
		producerRepository.save(producer);
		//Optional<Actor> actorIs = actorRepository.findById(actor.ge);
			Response response = ResponseHelper.statusResponse(200, "actor is added");
			return response;
		}
}
