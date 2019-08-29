
package com.bridgeit.fundoo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.bridgeit.fundoo.response.Response;
import com.bridgeit.fundoo.response.ResponseToken;
import com.bridgeit.fundoo.user.dto.LoginDTO;
import com.bridgeit.fundoo.user.dto.UserDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestUserController {

	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Mock
	private ResponseEntity<Response> statusResponse;

	private UserDTO userdto;
	private LoginDTO logindto;

	@Before
	public void setUserDTO() {
		userdto = new UserDTO();
	}

	@Before
	public void setLoginDTO() {
		logindto = new LoginDTO();
	}

	@Test
	public void register() {
		userdto.setFirstName("samadhan");
		userdto.setLastName("mali");
		userdto.setEmailId("samadhanmahajan73@gmail.com");
		userdto.setPassword("samadhan@123");
		userdto.setMobileNumber("8965231470");
		statusResponse = testRestTemplate.postForEntity("http://localhost:8762/user", userdto, Response.class);
		System.out.println(statusResponse);
		assertEquals(200,(testRestTemplate.postForEntity("http://localhost:8762/user", userdto, Response.class)).getBody().getStatusCode());
	}

	@Test
	public void userAuthentication() {
		logindto.setEmailId("samadhanmahajan73@gmail.com");
		logindto.setPassword("Samadhan@123");
		System.out.println(logindto);
		assertEquals(100, (testRestTemplate.postForEntity("http://localhost:8762/user/userLogin", logindto,
				ResponseToken.class)).getBody().getStatusCode());
	}
}
