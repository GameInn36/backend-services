package com.gameinn.user.service.service;

import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.repository.UserRepository;
import com.gameinn.user.service.util.UserObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRESTService {

    private final UserRepository userRepository;

    @Autowired
    UserRESTService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<UserReadDTO> getAllUsers(){
        return userRepository.findAll().stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
    }

    public UserReadDTO addUser(UserCreateUpdateDTO newUser){
        return UserObjectMapper.toReadDTO(userRepository.insert(UserObjectMapper.toEntity(newUser)));
    }

    public UserReadDTO getUserById(String Id){
        return UserObjectMapper.toReadDTO(userRepository.findUserById(Id));
    }

    public UserReadDTO getUserByEmailAndPassword(UserCreateUpdateDTO userCreateUpdateDTO){
        return UserObjectMapper.toReadDTO(userRepository.findUserByEmailAndPassword(userCreateUpdateDTO.getEmail(), userCreateUpdateDTO.getPassword()));
    }

    public List<UserReadDTO> getUserByUsername(String userName){
        return userRepository.findByUsernameStartingWithOrderByUsername(userName).stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
    }

    public UserReadDTO updateUserField(String userId, UserCreateUpdateDTO userCreateUpdateDTO){
        User user = UserObjectMapper.mapUser(userCreateUpdateDTO,userRepository.findUserById(userId));
        return UserObjectMapper.toReadDTO(userRepository.save(user));
    }
}
