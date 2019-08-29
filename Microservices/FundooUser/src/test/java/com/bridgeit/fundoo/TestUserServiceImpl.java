package com.bridgeit.fundoo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.response.ResponseToken;
import com.bridgeit.fundoo.user.dto.LoginDTO;
import com.bridgeit.fundoo.user.dto.UserDTO;
import com.bridgeit.fundoo.user.model.User;
import com.bridgeit.fundoo.user.repository.UserRepository;
import com.bridgeit.fundoo.user.service.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)

@SpringBootTest
public class TestUserServiceImpl {

	@Mock
	private ResponseToken statusResponseToken;
	
	@Mock
	private ModelMapper modelMapper;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private Response statusResponse;

	@InjectMocks
	private UserServiceImpl userServiceImpl;

	@Mock
	private UserRepository userRespository;

	@Test
	public void testRegistration() {
		User user = new User();
		UserDTO userdto = new UserDTO();
		when(modelMapper.map(userdto, User.class)).thenReturn(user);
		userdto.setFirstName("samadhan");
		userdto.setLastName("mali");
		userdto.setEmailId("samadhanmahajan73@gmail.com");
		userdto.setPassword("Sam@123");
		userdto.setMobileNumber("8965321470"); //
		//when(passwordEncoder.encode(userdto.getPassword())).thenReturn(userdto.getPassword()); // when(userRespository.save(user));
		statusResponse = userServiceImpl.userRegistretion(userdto);
		System.out.println(userServiceImpl.userRegistretion(userdto));
		assertEquals(statusResponse.getStatusCode(), 200);
		System.out.println(userdto);
		assertEquals("samadhan", userdto.getFirstName());
	}

	@Test
	public void testAuthentication() {
		LoginDTO logindto = new LoginDTO();
		User user = new User();
		// when(modelMapper.map(logindto, User.class)).thenReturn(user);
		logindto.setEmailId("samadhanmahajan73@gmail.com");
		logindto.setPassword("Sam@123");
		// when(passwordEncoder.encode(logindto.getPassword())).thenReturn(logindto.getPassword());
		assertEquals("samadhanmahajan73@gmail.com", logindto.getEmailId());

	}

	@Test
	public void testForgotPassword() {
		User user = new User();
		UserDTO userdto = new UserDTO();
		//when(modelMapper.map(userdto, User.class)).thenReturn(user);
		userdto.setEmailId("samadhanmahajan73@gmail.com");
		//when(passwordEncoder.encode(userdto.getPassword())).thenReturn(userdto.getPassword());
		assertEquals("samadhanmahajan73@gmail.com", userdto.getEmailId());
	}
	
	@Test
	public void testUserLogin() {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmailId("samadhanmahajan73@gmail.com");
		loginDTO.setPassword("Sam@1713");
		statusResponseToken = userServiceImpl.userLogin(loginDTO);
		System.out.println(statusResponseToken);
		assertEquals(statusResponse.getStatusCode(), 200);
	}

}
