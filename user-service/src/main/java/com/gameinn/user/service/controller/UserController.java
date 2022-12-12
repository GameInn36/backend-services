package com.gameinn.user.service.controller;

import com.gameinn.user.service.dto.UserDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.exception.InvalidEmailException;
import com.gameinn.user.service.service.UserRESTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> getUsers()
    {
        return userRESTService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId){
        return userRESTService.getUserById(userId);
    }

    @PostMapping("/validate")
    public boolean validateUser(@RequestBody UserDTO userDTO){
        return userRESTService.getUserByEmailAndPassword(userDTO) != null;
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserDTO newUser){
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(newUser);
        if(!violations.isEmpty()){
            throw new InvalidEmailException(violations.stream().findFirst().get().getMessage());
        }
        return ResponseEntity.ok(userRESTService.addUser(newUser));
    }
}
