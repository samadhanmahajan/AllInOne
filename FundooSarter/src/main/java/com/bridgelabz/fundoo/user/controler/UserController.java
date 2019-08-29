package com.bridgelabz.fundoo.user.controler;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.TokenResponse;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.service.UserService;

@RequestMapping("/User")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = { "*" }, exposedHeaders = { "token" })
public class UserController {

	@Autowired()
	UserService userService;

	/**
	 * Purpose : Rest API For Register User
	 * 
	 * @param userDTO
	 * @return
	 * @throws UserException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/Registretion")
	public ResponseEntity<Response> userRegistretion(@RequestBody UserDTO userDTO)
			throws UserException, UnsupportedEncodingException {

		Response response = userService.newUserRegistration(userDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);

	}

	/**
	 * @param emailId
	 * @param image
	 * @return
	 */
	@PutMapping(value = "/uploadprofilepic", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Response> uploadProfilePic(@RequestHeader String emailId, @RequestParam MultipartFile image) {
		Response response = userService.uploadImage(image, emailId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * @param emailId
	 * @return
	 */
	@GetMapping("/getuploadedimage/{emailId}")
	public Resource getUploadImage(@PathVariable String emailId) {
		Resource resourseStatus = userService.getUploadedImage(emailId);
		return resourseStatus;
	}

	/**
	 * Purpose : Rest API For user login
	 * 
	 * @param loginDTO
	 * @return
	 * @throws UserException
	 * @throws UnsupportedEncodingException
	 */
	@PostMapping("/Login")
	public ResponseEntity<TokenResponse> userLogin(@RequestBody LoginDTO loginDTO)
			throws UserException, UnsupportedEncodingException {

		TokenResponse response = userService.existenceUserLogin(loginDTO);
		System.out.println("login successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Purpose : Rest API For Email ID Verification
	 * 
	 * @param token
	 * @return
	 * @throws UserException
	 */
	@GetMapping(value = "/{token}")
	public ResponseEntity<Response> validateEmailID(@PathVariable String token) throws UserException {

		Response response = userService.validationOfEmailId(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	/**
	 * Purpose : Rest API For send reset password link
	 * 
	 * @param emailID
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws UserException
	 * @throws MessagingException
	 */
	@GetMapping("/SendResetPasswordLink")
	public ResponseEntity<Response> forgotPassword(@RequestParam("emailID") String emailID)
			throws UnsupportedEncodingException, UserException, MessagingException {

		Response status = userService.forgetPasswordLink(emailID);
		return new ResponseEntity<Response>(status, HttpStatus.OK);

	}

	/**
	 * Purpose : Rest API For password Reset
	 * 
	 * @param token
	 * @param password
	 * @return
	 * @throws UserException
	 */
	@GetMapping(value = "/PasswordReset")
	public ResponseEntity<Response> resetPassword(@RequestParam("emailID") String emailID,
			@RequestParam("password") String password) throws UserException {
		Response response = userService.resetUserPassword(emailID, password);
		return new ResponseEntity<Response>(response, HttpStatus.OK);

	}

}