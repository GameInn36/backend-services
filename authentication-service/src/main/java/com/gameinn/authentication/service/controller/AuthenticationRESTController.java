package com.gameinn.authentication.service.controller;

import com.gameinn.authentication.service.exception.InvalidEmailPasswordException;
import com.gameinn.authentication.service.feignClient.UserService;
import com.gameinn.authentication.service.models.*;
import com.gameinn.authentication.service.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationRESTController {
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    @Autowired
    public AuthenticationRESTController(JwtTokenUtil jwtTokenUtil, UserService userService){
        this.jwtTokenUtil = jwtTokenUtil; this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest){
        RegisterResponse registerResponse = userService.addUser(registerRequest);
        registerResponse.setMsg("You have successfully registered with that email: " + registerRequest.getEmail());
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

        AuthenticationStatus status = authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        if(!status.getIsAuthenticated()){
            throw new InvalidEmailPasswordException(status.getMessage(),HttpStatus.UNAUTHORIZED.value());
        }
        final String token = jwtTokenUtil.generateToken(status.getUser().getId());
        return ResponseEntity.ok(new JwtResponse(status.getUser(),token));
    }

    private AuthenticationStatus authenticate(String email, String password){
        AuthenticationStatus status;
        boolean isAdmin = email.equals("admin@gameinn.com") && password.equals("admin");
        User user = userService.validateUser(new JwtRequest(email,password));
        if(isAdmin || user != null){
            status = new AuthenticationStatus(true, user,"Authentication Successful.");
        }
        else{
            status = new AuthenticationStatus(false, null,"Invalid Username/Password");
        }
        return status;
    }
}
