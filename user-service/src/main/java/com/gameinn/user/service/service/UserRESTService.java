package com.gameinn.user.service.service;

import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRESTService {

    private final UserRepository userRepository;

    @Autowired
    UserRESTService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
