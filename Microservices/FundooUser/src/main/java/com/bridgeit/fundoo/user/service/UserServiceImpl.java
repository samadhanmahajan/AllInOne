package com.bridgeit.fundoo.user.service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeit.fundoo.exception.UserException;
import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.response.ResponseToken;
import com.bridgeit.fundoo.user.dto.LoginDTO;
import com.bridgeit.fundoo.user.dto.UserDTO;
import com.bridgeit.fundoo.user.model.MailModel;
import com.bridgeit.fundoo.user.model.User;
import com.bridgeit.fundoo.user.repository.UserRepository;
import com.bridgeit.fundoo.utility.RabbitMqSenderImpl;
import com.bridgeit.fundoo.utility.ResponseHelper;
import com.bridgeit.fundoo.utility.TokenGenerator;
import com.bridgeit.fundoo.utility.Utility;

@PropertySource("classpath:message.properties")
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenGenerator tokenUtil;

	@Autowired
	private Response statusResponse;

	@Autowired
	private Environment environment;

	@Autowired
	private RabbitMqSenderImpl rabbitMqSenderImpl;

	@Autowired
	private Logger logger;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private MailModel mailModel;

	@Override
	public Response userRegistretion(UserDTO userDto) {
		// logger.info("in user registretion");

		Optional<User> userPresent = userRepository.findByEmailId(userDto.getEmailId());
		if (userPresent.isPresent()) {
			return new Response("email is already exist", 301);
		}
		// encode user password
		User user = modelMapper.map(userDto, User.class);
		String password = passwordEncoder.encode(userDto.getPassword());
		user.setPassword(password);
		user = userRepository.save(user);
		/*
		 * logger.info("user register"); Long userId = user.getUserId(); String url; try
		 * { url = Utility.getUrl(userId); System.out.println(url);
		 * 
		 * mailModel.setBody(url + "/valid"); mailModel.setTo(userDto.getEmailId());
		 * 
		 * rabbitMqSenderImpl.sendMessageToQueue(mailModel);
		 * rabbitMqSenderImpl.send(mailModel);
		 * 
		 * System.out.println("UserServiceImpl.onRegister()::email sent successfully");
		 * 
		 * } catch (IllegalArgumentException e) {
		 * 
		 * e.pr\intStackTrace(); }
		 */ statusResponse = ResponseHelper.statusResponse(200, "successfully register");

		return statusResponse;

	}

	@Override
	public ResponseToken userLogin(LoginDTO loginDto) throws UserException {
		Optional<User> user = userRepository.findByEmailId(loginDto.getEmailId());

		if (user.isPresent()) {

			return authentication(user, loginDto.getPassword());

		}
		return new ResponseToken("email id not found", 400, "");

	}

	@Override
	public ResponseToken authentication(Optional<User> user, String password) {

		ResponseToken response = new ResponseToken();
		if (user.get().isVerify()) {
			boolean status = passwordEncoder.matches(password, user.get().getPassword());

			if (status == true) {
				String token = tokenUtil.createToken(user.get().getUserId());
				response.setStatusCode(200);
				response.setToken(token);
				response.setStatusMessage(environment.getProperty("user.login"));

				return response;
			}

			throw new UserException(401, environment.getProperty("user.login.password"));
		}

		throw new UserException(401, environment.getProperty("user.login.verification"));

	}

	@Override
	public Response validateEmailId(long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserException(404, environment.getProperty("user.validation.email")));
		user.setVerify(true);
		userRepository.save(user);
		statusResponse = ResponseHelper.statusResponse(200, environment.getProperty("user.validation"));

		return statusResponse;
	}

	@Override
	public Response forgetPassword(String emailId) throws UserException, UnsupportedEncodingException {
		Optional<User> useralreadyPresent = userRepository.findByEmailId(emailId);
		try {
			if (!useralreadyPresent.isPresent()) {

				throw new UserException(401, environment.getProperty("user.forgetpassword.emaiId"));
			}
		} catch (Exception e) {
			Long id = useralreadyPresent.get().getUserId();
			Utility.send(emailId, "password reset mail", Utility.getUrl(id));
		}
		return ResponseHelper.statusResponse(200, environment.getProperty("user.forgetpassword.link"));
	}

	@Override
	public Response resetPassword(long id, String password) {

		logger.info("id:" + id);

		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserException(404, environment.getProperty("user.resetpassword.user")));
		logger.info("user:" + user);
		String encodedpassword = passwordEncoder.encode(password);
		user.setPassword(encodedpassword);
		try {
			userRepository.save(user);
			return ResponseHelper.statusResponse(200, environment.getProperty("user.resetpassword.reset"));
		} catch (Exception e) {
			return new Response("user not found", 400);
		}
	}

}
