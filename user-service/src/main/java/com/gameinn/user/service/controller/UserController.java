package com.gameinn.user.service.controller;

import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.exception.InvalidCredentialsException;
import com.gameinn.user.service.service.UserRESTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRESTService userRESTService;
    private final Validator validator;
    @Autowired
    UserController(UserRESTService userRESTService, Validator validator){
        this.userRESTService = userRESTService;
        this.validator = validator;
    }
    @GetMapping("/")
    public List<UserReadDTO> getUsers(@RequestParam(required = false) String username)
    {
        List<UserReadDTO> users;
        if(username!=null){
            users = userRESTService.getUserByUsername(username);
        }
        else{
            users = userRESTService.getAllUsers();
        }
        return users;
    }
    @GetMapping("/{userId}")
    public UserReadDTO getUserById(@PathVariable String userId){
        return userRESTService.getUserById(userId);
    }

    @PostMapping("/validate")
    public UserReadDTO validateUser(@RequestBody UserCreateUpdateDTO userCreateUpdateDTO){
        return userRESTService.getUserByEmailAndPassword(userCreateUpdateDTO);
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserCreateUpdateDTO newUser){
        Set<ConstraintViolation<UserCreateUpdateDTO>> violations = validator.validate(newUser);
        if(!violations.isEmpty()){
            throw new InvalidCredentialsException(violations.stream().findFirst().get().getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
        }
        return ResponseEntity.ok(userRESTService.addUser(newUser));
    }

    @PatchMapping("/{userId}")
    public UserReadDTO updateUserField(@PathVariable String userId, @RequestBody UserCreateUpdateDTO userCreateUpdateDTO){
        Set<ConstraintViolation<UserCreateUpdateDTO>> violations = validator.validate(userCreateUpdateDTO);
        if(!violations.isEmpty()){
            throw new InvalidCredentialsException(violations.stream().findFirst().get().getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
        }
        return userRESTService.updateUserField(userId, userCreateUpdateDTO);
    }
}
