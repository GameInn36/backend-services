package com.gameinn.authentication.service.controller;

import com.gameinn.authentication.service.models.AuthenticationStatus;
import com.gameinn.authentication.service.models.ErrorResponseDTO;
import com.gameinn.authentication.service.models.JwtRequest;
import com.gameinn.authentication.service.models.JwtResponse;
import com.gameinn.authentication.service.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthenticationRESTController {
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public AuthenticationRESTController(JwtTokenUtil jwtTokenUtil){
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

        AuthenticationStatus status = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if(!status.getIsAuthenticated()){
            List<String> details = new ArrayList<>();
            details.add(status.getMessage());
            ErrorResponseDTO error = new ErrorResponseDTO(new Date(), HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED", details, "aa");
            return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
        }
        final String token = jwtTokenUtil.generateToken(authenticationRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private AuthenticationStatus authenticate(String username, String password){
        AuthenticationStatus status;

        if(!username.equals("admin") || !password.equals("admin")){
            status = new AuthenticationStatus(false,"Invalid Username/Password");
        }
        else{
            status = new AuthenticationStatus(true,"Authentication Successful.");
        }
        return status;
    }
}
