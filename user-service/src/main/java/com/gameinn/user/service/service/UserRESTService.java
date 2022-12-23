package com.gameinn.user.service.service;

import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.exception.UserNotFoundException;
import com.gameinn.user.service.repository.UserRepository;
import com.gameinn.user.service.util.UserObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return UserObjectMapper.toReadDTO(
                userRepository.findUserById(Id)
                        .orElseThrow(() -> new UserNotFoundException("There is no user matches with given userId", HttpStatus.NOT_FOUND.value())));
    }

    public UserReadDTO getUserByEmailAndPassword(UserCreateUpdateDTO userCreateUpdateDTO){
        User user = userRepository.findUserByEmailAndPassword(userCreateUpdateDTO.getEmail(), userCreateUpdateDTO.getPassword())
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given email-password", HttpStatus.NOT_FOUND.value()));
        return UserObjectMapper.toReadDTO(user);
    }

    public List<UserReadDTO> getUserByUsername(String userName){
        return userRepository.findByUsernameStartingWithOrderByUsername(userName).stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
    }

    public UserReadDTO updateUser(String userId, UserCreateUpdateDTO userCreateUpdateDTO){
        User user = UserObjectMapper.mapUser(userCreateUpdateDTO, userRepository
                .findUserById(userId).orElseThrow(() ->new UserNotFoundException("There is no user matches with given userId", HttpStatus.NOT_FOUND.value())));
        return UserObjectMapper.toReadDTO(userRepository.save(user));
    }
}
