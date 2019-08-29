package com.bridgeit.fundoo.user.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bridgeit.fundoo.exception.UserException;
import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.response.ResponseToken;
import com.bridgeit.fundoo.user.dto.LoginDTO;
import com.bridgeit.fundoo.user.dto.UserDTO;
import com.bridgeit.fundoo.user.service.UserService;
import com.bridgeit.fundoo.utility.TokenGenerator;

@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	private Logger logger;

	@Autowired
	private UserService userService;

	@Autowired
	private TokenGenerator tokenUtil;
	
	@Autowired
	private RestTemplate restTemplate;
	@PostMapping
	public ResponseEntity<Response> register(@RequestBody @Valid UserDTO userDto)
			throws UserException, UnsupportedEncodingException {
		logger.info("in register api");
		Response response = userService.userRegistretion(userDto);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PostMapping("/userLogin")
	public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO)
			throws UserException, UnsupportedEncodingException {
		logger.info("in login api");
		ResponseToken response = userService.userLogin(loginDTO);
//		String url = "http://localhost:8017/note/getAllNote";
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("token", response.getToken());
//		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> entity = new HttpEntity<String>(headers);
//		System.out.println("header-->" + headers); 
//		
//		ResponseEntity<Object[]> notes = restTemplate.exchange(url, HttpMethod.GET, entity, Object[].class);
//		
//		System.out.println(notes.getBody());
		List<Object> images = restTemplate.getForObject("http://localhost:8673/note/getAllNote", List.class);
		return new ResponseEntity<>(images, HttpStatus.OK);
	}

	@PutMapping("/forgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam String emailId)
			throws UnsupportedEncodingException, UserException, MessagingException {
		logger.info("in forgotPassword api");
		Response status = userService.forgetPassword(emailId);

		return new ResponseEntity<>(status, HttpStatus.OK);

	}

	@PutMapping(value = "/resetPassword/{token}")
	public ResponseEntity<Response> resetPassword(@RequestParam String token, @RequestParam String password)
			throws UserException {
		logger.info("in resetPassword api");
		long id = tokenUtil.decodeToken(token);
		Response response = userService.resetPassword(id, password);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@PutMapping(value = "/{token}/valid")
	public ResponseEntity<Response> emailValidation(@PathVariable String token) throws UserException {
		logger.info("in emailValidation api");
		long id = tokenUtil.decodeToken(token);
		Response response = userService.validateEmailId(id);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}