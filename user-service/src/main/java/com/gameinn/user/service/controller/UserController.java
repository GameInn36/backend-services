package com.gameinn.user.service.controller;

import com.gameinn.user.service.dto.ErrorResponseDTO;
import com.gameinn.user.service.dto.UserDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.service.UserRESTService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRESTService userRESTService;
    @Autowired
    UserController(UserRESTService userRESTService){
        this.userRESTService = userRESTService;
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
        log.debug(userDTO.getEmail() + " " + userDTO.getPassword());
        return userRESTService.getUserByEmailAndPassword(userDTO) != null;
    }

    @PostMapping("/")
    public ResponseEntity<?> addUser(@RequestBody UserDTO newUser){
        if(newUser.getEmail().equals("invalid")){
            ErrorResponseDTO error = new ErrorResponseDTO(new Date(),HttpStatus.NOT_ACCEPTABLE.value(),"NOT_ACCEPTABLE","invalid email","/user/");
            return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(userRESTService.addUser(newUser));
    }
}
