package com.gameinn.user.service.controller;

import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.service.UserRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
