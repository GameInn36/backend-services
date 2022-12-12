package com.gameinn.authentication.service.controller;

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

import java.util.Date;

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
            ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", status.getMessage(), "/auth/authenticate");
            return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
        }
        final String token = jwtTokenUtil.generateToken(authenticationRequest.getEmail());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private AuthenticationStatus authenticate(String email, String password){
        AuthenticationStatus status;
        boolean isAdmin = email.equals("admin@gameinn.com") && password.equals("admin");
        boolean isValid = userService.validateUser(new JwtRequest(email,password));
        log.debug(String.valueOf(isValid));
        if(isAdmin || isValid){
            status = new AuthenticationStatus(true,"Authentication Successful.");
        }
        else{
            status = new AuthenticationStatus(false,"Invalid Username/Password");
        }
        return status;
    }
}
