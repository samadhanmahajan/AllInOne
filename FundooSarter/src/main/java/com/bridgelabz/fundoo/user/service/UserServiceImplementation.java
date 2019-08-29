package com.bridgelabz.fundoo.user.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.exception.UserException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.response.TokenResponse;
import com.bridgelabz.fundoo.user.dto.LoginDTO;
import com.bridgelabz.fundoo.user.dto.UserDTO;
import com.bridgelabz.fundoo.user.model.EmailInfo;
import com.bridgelabz.fundoo.user.model.User;
import com.bridgelabz.fundoo.user.repository.UserRepository;
import com.bridgelabz.fundoo.utility.MailServiceUtility;
import com.bridgelabz.fundoo.utility.ResponseHelper;
import com.bridgelabz.fundoo.utility.SenderQueue;
import com.bridgelabz.fundoo.utility.TokenUtility;

@PropertySource("classpath:ExceptinMessages.properties")
@Service("userService")
public class UserServiceImplementation implements UserService {

	private final Path fileLocation = java.nio.file.Paths.get("/home/mobicomp");

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private Logger logger;

	@Autowired
	private EmailInfo emailInfo;

	@Autowired
	private SenderQueue senderQueue;

	@Autowired
	private MailServiceUtility mailServiceUtility;

	@Autowired
	private Response response;

	@Autowired
	private Environment environment;

	@Autowired
	private TokenUtility tokenUtility;

	public static String Key = "user";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;


	@Override
	public Response uploadImage(MultipartFile imageUpload, String emailID) {

		String token = (String) redisTemplate.opsForHash().get(Key, emailID);
		Long userID = tokenUtility.decodeToken(token);

		Optional<User> user = userRepository.findById(userID);

		if (!user.isPresent()) {
			throw new UserException(-5, "user is not present");
		}

		UUID uuid = UUID.randomUUID();

		String uniqueId = uuid.toString();
		try {
			Files.copy(imageUpload.getInputStream(), fileLocation.resolve(uniqueId), StandardCopyOption.REPLACE_EXISTING);
			user.get().setProfilePic(uniqueId);
			userRepository.save(user.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseHelper.sendError(200, "profile picture is uploaded");
	}

	@Override
	public Resource getUploadedImage(String emailID) {

		String token = (String) redisTemplate.opsForHash().get(Key, emailID);
		Long userID = tokenUtility.decodeToken(token);

		Optional<User> user = userRepository.findById(userID);
		if (!user.isPresent()) {
			throw new UserException(-5, "user already exist");
		}

		try {
			Path imageFile = fileLocation.resolve(user.get().getProfilePic());

			Resource resource = new UrlResource(imageFile.toUri());

			if (resource.exists() || (resource.isReadable())) {
				System.out.println(resource);
				return resource;
			} else {
				throw new Exception("Couldn't read file: " + imageFile);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Response newUserRegistration(UserDTO userDTO) {

		Optional<User> userIsPresent = userRepository.findAll().stream()
				.filter(data -> data.getEmailId().equals(userDTO.getEmailId())).findFirst();
		if (userIsPresent.isPresent()) {
			throw new UserException(201, environment.getProperty("status.register.emailExistError"));
			
		}
		System.out.println(userIsPresent.isPresent());

		String password = passwordEncoder.encode(userDTO.getPassword());

		User user = modelMapper.map(userDTO, User.class);
		user.setPassword(password);
		user = userRepository.save(user);

		emailInfo.setTo(userDTO.getEmailId());
		emailInfo.setSubject("Email Verification ");

		try {
			emailInfo.setBody(mailServiceUtility.getLink("http://localhost:9090/User/", user.getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		// mailServiceUtility.send(emailInfo);

		try {
			senderQueue.produce(emailInfo);
		} catch (java.lang.Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String token = tokenUtility.genetateToken(user.getUserId());
		redisTemplate.opsForHash().put(Key, user.getEmailId(), token);
		response = ResponseHelper.sendError(201, environment.getProperty("status.register.success"));
		return response;

	}

	@Override
	public TokenResponse existenceUserLogin(LoginDTO loginDTO) throws UserException, UnsupportedEncodingException {

		User user = userRepository.findAll().stream().filter(data -> data.getEmailId().equals(loginDTO.getEmailId()))
				.findFirst().orElseThrow(() -> new UserException(environment.getProperty("status.login.invalidInput")));
		System.out.println("login successfully1");
		TokenResponse response = new TokenResponse();

		if (user.isVerify()) {

			boolean status = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
			System.out.println(status);
			if (status) {

				String token = tokenUtility.genetateToken(user.getUserId());
				response.setToken(token);
				response.setStatusCode(200);
				response.setStatusMessage(environment.getProperty("status.login.success"));
				redisTemplate.opsForHash().put(Key, user.getEmailId(), token);
				return response;
			}

			throw new UserException(401, environment.getProperty("user.login.password"));
		}

		throw new UserException(401, environment.getProperty("user.login.verification"));

	}

	@Override
	public Response validationOfEmailId(String token) {
		Long userID = tokenUtility.decodeToken(token);
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new UserException(404, environment.getProperty("status.user.notExist")));
		user.setVerify(true);
		userRepository.save(user);
		response = ResponseHelper.sendError(200, environment.getProperty("status.email.verified"));
		return response;
	}

	@Override
	public Response resetUserPassword(String emailID, String password) {
		String token = (String) redisTemplate.opsForHash().get(Key, emailID);
		Long userID = tokenUtility.decodeToken(token);
		User user = userRepository.findById(userID)
				.orElseThrow(() -> new UserException(404, environment.getProperty("user.resetpassword.user")));
		String encodedpassword = passwordEncoder.encode(password);
		user.setPassword(encodedpassword);
		userRepository.save(user);
		return ResponseHelper.sendError(200, environment.getProperty("status.resetPassword.success"));

	}

	@Override
	public Response forgetPasswordLink(String emailID) throws UserException, UnsupportedEncodingException {
		User user = userRepository.findAll().stream().filter(data -> data.getEmailId().equals(emailID)).findFirst()
				.orElseThrow(() -> new UserException(environment.getProperty("status.email.invalidMail")));

		emailInfo.setTo(emailID);
		emailInfo.setSubject("Forget Password Link ");
		emailInfo.setBody("http://localhost:4200/forgotpassword");

		mailServiceUtility.send(emailInfo);

		return ResponseHelper.sendError(200, environment.getProperty("status.forgetPassword.success"));
	}

}
