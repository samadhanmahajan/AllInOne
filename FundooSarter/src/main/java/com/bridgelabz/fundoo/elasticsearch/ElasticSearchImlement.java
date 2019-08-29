package com.bridgelabz.fundoo.elasticsearch;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.note.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service("ElasticSearch")
public class ElasticSearchImlement implements ElasticSearch {

	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private ObjectMapper objectMapper;

	String Key = "user";
	String index_name = "fundoo";
	String type_name = "note";
	
	@Override
	public void deleteNote(Note note) {
		Map<String, Object> noteCreate = objectMapper.convertValue(note, Map.class);

		DeleteRequest request = new DeleteRequest(index_name, type_name, String.valueOf(note.getNoteId()));
		// request.(noteCreate);
		try {
			client.delete(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void createNote(Note note) {

		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.registerModule(new JavaTimeModule());
	
		Map<String, Object> dnote = objectMapper.convertValue(note, Map.class);

		IndexRequest indexRequest = new IndexRequest(index_name, type_name, String.valueOf(note.getNoteId()))
				.source(dnote);
		System.out.println(indexRequest);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

	@Override
	public void updateNote(Note note) {
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.registerModule(new JavaTimeModule());
		Map<String, Object> noteCreate = objectMapper.convertValue(note, Map.class);

		// System.out.println(environment.getProperty("status.notes.updatedSuccessfull"));
		UpdateRequest request = new UpdateRequest(index_name, type_name, String.valueOf(note.getNoteId()))
				.doc(noteCreate);
		System.out.println("request" + request);
		try {
			client.update(request, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void searchNote(Note note) {
		

	}

}
