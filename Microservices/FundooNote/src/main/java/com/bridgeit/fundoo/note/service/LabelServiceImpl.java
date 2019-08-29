
package com.bridgeit.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgeit.fundoo.exception.LabelException;
import com.bridgeit.fundoo.note.dto.LabelDTO;
import com.bridgeit.fundoo.note.model.Label;
import com.bridgeit.fundoo.note.model.Note;
import com.bridgeit.fundoo.note.repository.LabelRespository;
import com.bridgeit.fundoo.note.repository.NoteRepository;
import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.utility.ResponseHelper;
import com.bridgeit.fundoo.utility.TokenGenerator;

@Service("LableService")

@PropertySource("classpath:message.properties")
public class LabelServiceImpl implements LabelService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Environment enviornment;

	@Autowired
	private LabelRespository labelRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private Logger logger;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	//@Value("${key}")
	//private String Key;

	@Override
	public Response createLabel(LabelDTO labeldto, long userId) {
		// Optional<User> user = userRepository.findById(userId);

		Label label = modelMapper.map(labeldto, Label.class);

		label.setLabelName(labeldto.getLabelName());
		label.setUserId(userId);
		label.setCreated(LocalDateTime.now());
		label.setModified(LocalDateTime.now());
		labelRepository.save(label);
		// userRepository.save(user.get());

		Response response = ResponseHelper.statusResponse(200, enviornment.getProperty("status.label.created"));

		return response;
	}

	@Override
	public Response deleteLabel(long userId, long labelId) {

		Label label = labelRepository.findByLabelIdAndUserId(labelId, userId);
		if (label == null) {
			throw new LabelException(101, enviornment.getProperty("label not exist"));
		}
		labelRepository.delete(label);

		Response response = ResponseHelper.statusResponse(400, enviornment.getProperty("status.label.deleted"));
		return response;
	}
	
	@Override
	public String getToken(String email) { // return return (String)
		//return (java.lang.String) redisTemplate.opsForHash().get(Key, email);
		return "";
	}
}
