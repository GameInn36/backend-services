package com.gameinn.user.service.controller;

import com.gameinn.user.service.dataTypes.GameLog;
import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.exception.InvalidCredentialsException;
import com.gameinn.user.service.model.Game;
import com.gameinn.user.service.service.UserRESTService;
import com.gameinn.user.service.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping("/{userId}/toPlayList")
    public List<Game> getToPlayList(@PathVariable String userId){
        return userRESTService.getToPlayList(userId);
    }

    @GetMapping("/{userId}/followers")
    public List<UserReadDTO> getFollowers(@PathVariable String userId){
        return userRESTService.getFollowers(userId);
    }

    @GetMapping("/{userId}/following")
    public List<UserReadDTO> getFollowing(@PathVariable String userId){
        return userRESTService.getFollowing(userId);
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

    @PostMapping("/{userId}/log/")
    public UserReadDTO addGameLog(@PathVariable String userId, @RequestBody GameLog log){
        return userRESTService.addGameLog(userId,log);
    }

    @PutMapping("/{userId}")
    public UserReadDTO updateUser(@PathVariable String userId, @RequestBody UserCreateUpdateDTO userCreateUpdateDTO){
        Set<ConstraintViolation<UserCreateUpdateDTO>> violations = validator.validate(userCreateUpdateDTO);
        if(!violations.isEmpty()){
            throw new InvalidCredentialsException(violations.stream().findFirst().get().getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
        }
        return userRESTService.updateUser(userId, userCreateUpdateDTO);
    }

    @PutMapping("/follow/{userId}")
    public UserReadDTO followUser(HttpServletRequest request, @PathVariable String userId)
    {
        String requestOwnerId = JwtUtil.getSubject(JwtUtil.getToken(request));
        return userRESTService.followUser(requestOwnerId, userId);
    }

    @PutMapping("/unfollow/{userId}")
    public UserReadDTO unfollowUser(HttpServletRequest request, @PathVariable String userId)
    {
        String requestOwnerId = JwtUtil.getSubject(JwtUtil.getToken(request));
        return userRESTService.unfollowUser(requestOwnerId, userId);
    }
}
