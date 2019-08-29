package com.bridgeit.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bridgeit.fundoo.exception.UserException;
import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.response.ResponseToken;
import com.bridgeit.fundoo.user.dto.LoginDTO;
import com.bridgeit.fundoo.user.dto.UserDTO;
import com.bridgeit.fundoo.user.model.User;


@Service
public interface UserService {

	Response userRegistretion(UserDTO userDto) throws UserException;

	ResponseToken userLogin(LoginDTO loginDto) throws UserException;

	ResponseToken authentication(Optional<User> user, String password);

	Response validateEmailId(long id);
	
	Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException;
	
	Response resetPassword(long id, String password);
	
}
