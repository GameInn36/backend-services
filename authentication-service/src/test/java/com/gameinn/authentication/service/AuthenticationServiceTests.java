package com.gameinn.authentication.service;

import com.gameinn.authentication.service.controller.AuthenticationRESTController;
import com.gameinn.authentication.service.feignClient.UserService;
import com.gameinn.authentication.service.models.*;
import com.gameinn.authentication.service.util.JwtTokenUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;

@SpringBootTest
class AuthenticationServiceTests {
	@InjectMocks
	private AuthenticationRESTController authenticationRESTController;
	@Mock
	private UserService userService;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	@BeforeTestClass
	public void setUp(){
		MockitoAnnotations.openMocks(this);
	}
	@Test
	void authenticateUserTest(){
		User user = new User();
		user.setEmail("test@gameinn.com");
		user.setId("test");
		Mockito.when(userService.validateUser(new JwtRequest("test@gameinn.com","test")))
				.thenReturn(user);

		ResponseEntity<?> response = authenticationRESTController.createAuthenticationToken(new JwtRequest("test@gameinn.com","test"));
		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
		Assertions.assertEquals(((JwtResponse)response.getBody()).getUser().getEmail(),user.getEmail());
	}

	@Test
	void registerUser(){
		RegisterResponse registerResponse = new RegisterResponse("test@gameinn.com","");
		Mockito.when(userService.addUser(new RegisterRequest("test","test@gameinn.com","test")))
				.thenReturn(registerResponse);

		ResponseEntity<?> response = authenticationRESTController.registerUser(new RegisterRequest("test","test@gameinn.com","test"));

		Assertions.assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
		Assertions.assertEquals(((RegisterResponse)response.getBody()).getEmail(),registerResponse.getEmail());
	}
}
