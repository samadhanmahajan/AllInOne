package com.bridgeit.fundoo.note.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bridgeit.fundoo.elasticsearch.ElasticSearch;
import com.bridgeit.fundoo.exception.NoteException;
import com.bridgeit.fundoo.note.dto.NoteDTO;
import com.bridgeit.fundoo.note.model.Note;

import com.bridgeit.fundoo.note.repository.NoteRepository;
import com.bridgeit.fundoo.response.Response;
//import com.bridgeit.fundoo.user.model.User;
//import com.bridgeit.fundoo.user.repository.UserRepository;
import com.bridgeit.fundoo.utility.ResponseHelper;

/**
 * @author user
 *
 */
@Service("notesService")
@PropertySource("classpath:message.properties")
public class NoteServiceImp implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Environment enviornment;

	@Autowired
	private ElasticSearch elasticSearch;

	@Autowired
	private Logger logger;

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	//@Value("${key}")
	//private String Key;

	@Override
	public Response createNote(NoteDTO notedto, long userId) {
		// userId = 23;
		if (notedto.getTitle().isEmpty() && notedto.getDescription().isEmpty()) {

			throw new NoteException(-5, enviornment.getProperty("status.notes.empty"));

		}

		Note note = modelMapper.map(notedto, Note.class);
		// Optional<Note> note1 = noteRepository.findByUserId(userId);

		Response response = null;
		try {
			// if (note1.isPresent()) {
			note.setUserId(userId);
			note.setCreated(LocalDateTime.now());
			note.setModified(LocalDateTime.now());
			// note1.get().getNotes().add(note);
			// note1.get().getNoteId();
			// System.out.println(notes.getTitle());
			noteRepository.save(note);
			// userRepository.save(user.get());
			elasticSearch.createNote(note);

			// }
		} catch (Exception e) {
			// Response response1=new Response("user not found",400);
			throw new NoteException(500, "user not found ");
		}
		response = ResponseHelper.statusResponse(100, enviornment.getProperty("status.notes.createdSuccessfull"));
		return response;

	}

	@Override
	public Response updateNote(NoteDTO noteDto, long userId, long noteId) {
		try {
			if (noteDto.getTitle().isEmpty() && noteDto.getDescription().isEmpty()) {
				throw new NoteException(-5, enviornment.getProperty("status.notes.empty"));
			}
			Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

			note.setTitle(noteDto.getTitle());
			note.setDescription(noteDto.getDescription());
			note.setModified(LocalDateTime.now());
			noteRepository.save(note);
			elasticSearch.updateNote(note);
		} catch (Exception e) {
			throw new NoteException(500, "user not found");
		}
		Response response = ResponseHelper.statusResponse(200,
				enviornment.getProperty("status.notes.updatedSuccessfull"));
		return response;
	}

	@Override
	public Response retrieveNote(long userId, long noteId) {

		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);
		String title = note.getTitle();
		String description = note.getDescription();
		System.out.println("titel "+title+" disc "+description);
		Response response = ResponseHelper.statusResponse(200, "retrieved successfully");
		return response;
	}

	@Override
	public Response deleteNote(long userId, long noteId) {
		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		if (note.isTrash() == false) {
			note.setTrash(true);
			note.setModified(LocalDateTime.now());
			noteRepository.save(note);
			elasticSearch.deleteNote(noteId);
			Response response = ResponseHelper.statusResponse(100, enviornment.getProperty("status.note.trashed"));
			return response;
		}

		Response response = ResponseHelper.statusResponse(100, enviornment.getProperty("status.note.trashError"));
		return response;
	}

	@Override
	public Response deleteNotePermenantly(long userId, long noteId) {

		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		if (note.isDeletePermantely() == false) {
			note.setDeletePermantely(true);
			note.setModified(LocalDateTime.now());
			noteRepository.save(note);
			Response response = ResponseHelper.statusResponse(200, enviornment.getProperty("status.note.deleted"));
			return response;
		} else {
			Response response = ResponseHelper.statusResponse(100, enviornment.getProperty("status.note.noteDeleted"));
			return response;
		}
	}

	@Override
	public Response checkPin(long userId, long noteId) {

		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		if (note == null) {
			throw new NoteException(100, enviornment.getProperty("note is not exist"));
		}

		if (note.isPin() == false) {
			note.setPin(true);
			noteRepository.save(note);

			Response response = ResponseHelper.statusResponse(200, enviornment.getProperty("status.note.pinned"));
			return response;
		} else {
			note.setPin(false);
			noteRepository.save(note);
			Response response = ResponseHelper.statusResponse(200, enviornment.getProperty("status.note.unpinned"));
			return response;
		}

	}

	@Override
	public Response archived(long userId, long noteId) {

		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		if (note == null) {
			throw new NoteException(100, "note is not exist");
		}

		if (note.isArchive() == false) {
			note.setArchive(true);
			noteRepository.save(note);

			Response response = ResponseHelper.statusResponse(200, enviornment.getProperty("status.note.archieved"));
			return response;
		} else {
			note.setArchive(false);
			noteRepository.save(note);
			Response response = ResponseHelper.statusResponse(200, enviornment.getProperty("status.note.unarchieved"));
			return response;
		}
	}

	@Override
	public List<Note> getAllNote(long userId) {
		/*
		 * Optional<User> user = userRepository.findById(userId);
		 * 
		 * if (!user.isPresent()) { throw new NoteException("invalid input"); }
		 */
		List<Note> notes = noteRepository.findByUserId(userId);
		List<Note> listnote = new ArrayList<>();

		for (Note note : notes) {
			Note notedto = modelMapper.map(note, Note.class);
			listnote.add(notedto);
		}

		return listnote;
	}

	@Override
	public Response setReminder(long userId, long noteId, String reminder) {

		LocalDateTime today = LocalDateTime.now(); // Today
		LocalDateTime tomorrow = today.plusDays(1); // tommorow
		LocalDateTime nextWeek = today.plusWeeks(1);
		// System.out.println(nextWeek);
		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		if (note == null) {
			throw new NoteException(-5, "invalid note");
		} else if (reminder.equalsIgnoreCase("today")) {
			note.setSetReminder(today);
		} else if (reminder.equalsIgnoreCase("tommorow")) {
			note.setSetReminder(tomorrow);
		} else if (reminder.equalsIgnoreCase("nextweek")) {
			note.setSetReminder(nextWeek);
		}
		/*
		 * System.out.println("time is" + reminder); DateTimeFormatter datetimeFormatter
		 * = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); LocalDateTime
		 * localDateTime = LocalDateTime.parse(reminder, datetimeFormatter);
		 * System.out.println(localDateTime); LocalDateTime currentDateAndTime =
		 * LocalDateTime.now(); System.out.println(currentDateAndTime); if
		 * (currentDateAndTime.compareTo(localDateTime) < 0) {
		 */
		note.setModified(LocalDateTime.now());
		noteRepository.save(note);
		Response response = ResponseHelper.statusResponse(100, enviornment.getProperty("note.status.remainder"));
		return response;
	}

	@Override
	public Response deleteReminder(long userId, long noteId) {
		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		if (note == null) {
			throw new NoteException(-5, "invalid note");
		}

		note.setSetReminder(null);
		noteRepository.save(note);

		Response response = ResponseHelper.statusResponse(100, enviornment.getProperty("note.status.deleteremainder"));
		return response;
	}

	@Override
	public LocalDateTime getParticularNoteReminder( long userId, long noteId) {
		Note note = noteRepository.findByUserIdAndNoteId(userId, noteId);

		LocalDateTime getRemiderNote = note.getSetReminder();
		return getRemiderNote;
	}

	@Override
	public String getToken(String gmail) { // return return (String)
		return "";
		//return (String) redisTemplate.opsForHash().get(Key, email);
	}
}
