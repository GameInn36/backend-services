package com.gameinn.user.service.service;

import com.gameinn.user.service.dto.UserDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.repository.UserRepository;
import com.gameinn.user.service.util.UserObjectMapper;
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

    public User addUser(UserDTO newUser){
        return userRepository.insert(UserObjectMapper.toEntity(newUser));
    }

    public User getUserById(String Id){
        return userRepository.findUserById(Id);
    }

    public User getUserByEmailAndPassword(UserDTO userDTO){
        return userRepository.findUserByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
    }

    public List<User> getUserByUserName(String userName){
        return userRepository.findByUserNameStartingWithOrderByUserName(userName);
    }
}
