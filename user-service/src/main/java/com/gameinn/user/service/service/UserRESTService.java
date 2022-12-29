package com.gameinn.user.service.service;

import com.gameinn.user.service.dataTypes.GameLog;
import com.gameinn.user.service.dto.GameLogDTO;
import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserProfilePageDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.entity.User;
import com.gameinn.user.service.exception.UserNotFoundException;
import com.gameinn.user.service.feignClient.GameService;
import com.gameinn.user.service.model.Game;
import com.gameinn.user.service.repository.UserRepository;
import com.gameinn.user.service.util.UserObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UserRESTService {

    private final UserRepository userRepository;
    private final GameService gameService;

    @Autowired
    UserRESTService(UserRepository userRepository, GameService gameService){
        this.userRepository = userRepository;
        this.gameService = gameService;
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

    public List<UserReadDTO> getUsersById(List<String> userIds){
        return ((List<User>) userRepository.findAllById(userIds)).stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
    }

    public UserReadDTO getUserByEmailAndPassword(UserCreateUpdateDTO userCreateUpdateDTO){
        User user = userRepository.findUserByEmailAndPassword(userCreateUpdateDTO.getEmail(), userCreateUpdateDTO.getPassword())
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given email-password", HttpStatus.NOT_FOUND.value()));
        return UserObjectMapper.toReadDTO(user);
    }

    public List<UserReadDTO> getUserByUsername(String userName){
        return userRepository.findByUsernameContainingIgnoreCaseOrderByUsername(userName).stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
    }

    public UserReadDTO updateUser(String userId, UserCreateUpdateDTO userCreateUpdateDTO){
        User user = UserObjectMapper.mapUser(userCreateUpdateDTO, userRepository
                .findUserById(userId).orElseThrow(() ->new UserNotFoundException("There is no user matches with given userId", HttpStatus.NOT_FOUND.value())));
        return UserObjectMapper.toReadDTO(userRepository.save(user));
    }

    public List<Game> getToPlayList(String userId){
        List<String> gameIds = userRepository.findUserById(userId).orElseThrow(() ->new UserNotFoundException("There is no user matches with given userId", HttpStatus.NOT_FOUND.value())).getToPlayList();
        if(gameIds == null){
            return new ArrayList<>();
        }
        return gameService.getAllGames(gameIds);
    }

    public UserReadDTO followUser(String sourceId, String destId)
    {
        User sourceUser = userRepository.findUserById(sourceId)
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + sourceId, HttpStatus.NOT_FOUND.value()));
        User destUser = userRepository.findUserById(destId)
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + destId, HttpStatus.NOT_FOUND.value()));

        if(sourceUser.getFollowing() == null)
        {
            sourceUser.setFollowing(new ArrayList<>());
        }

        if(destUser.getFollowers() == null)
        {
            destUser.setFollowers(new ArrayList<>());
        }

        sourceUser.getFollowing().add(destId);
        destUser.getFollowers().add(sourceId);

        userRepository.save(sourceUser);
        userRepository.save(destUser);

        return UserObjectMapper.toReadDTO(sourceUser);
    }

    public UserReadDTO unfollowUser(String sourceId, String destId)
    {
        User sourceUser = userRepository.findUserById(sourceId)
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + sourceId, HttpStatus.NOT_FOUND.value()));
        User destUser = userRepository.findUserById(destId)
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + destId, HttpStatus.NOT_FOUND.value()));


        Predicate<String> destIdFilter = a->(a.equals(destId));
        Predicate<String> sourceIdFilter = a->(a.equals(sourceId));

        sourceUser.getFollowing().removeIf(destIdFilter);
        destUser.getFollowers().removeIf(sourceIdFilter);

        userRepository.save(sourceUser);
        userRepository.save(destUser);

        return UserObjectMapper.toReadDTO(sourceUser);
    }

    public UserReadDTO addGameLog(String userId, GameLog log){
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + userId, HttpStatus.NOT_FOUND.value()));
        if(user.getLogs() == null){
            user.setLogs(new ArrayList<>());
        }
        user.getLogs().add(log);
        gameService.increaseLogCount(log.getGameId(), new Game());
        return UserObjectMapper.toReadDTO(userRepository.save(user));
    }

    public List<UserReadDTO> getFollowing(String userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + userId, HttpStatus.NOT_FOUND.value()));
        if(user.getFollowing() != null && user.getFollowing().size() != 0){
            return ((List<User>) userRepository.findAllById(user.getFollowing())).stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public List<UserReadDTO> getFollowers(String userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + userId, HttpStatus.NOT_FOUND.value()));
        if(user.getFollowers() != null && user.getFollowers().size() != 0){
            return ((List<User>) userRepository.findAllById(user.getFollowers())).stream().map(UserObjectMapper::toReadDTO).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    //TODO:BURADA KALDIM UNIT TEST İÇİN
    public List<Game> getFavoriteGames(String userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + userId, HttpStatus.NOT_FOUND.value()));
        if(user.getFavoriteGames() != null && user.getFavoriteGames().size() != 0){
            return gameService.getAllGames(user.getFavoriteGames());
        }
        return new ArrayList<>();
    }

    public List<GameLogDTO> getGameLogs(String userId){
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + userId, HttpStatus.NOT_FOUND.value()));
        if(user.getLogs() != null && user.getLogs().size() != 0){
            List<String> gameIds = user.getLogs().stream().map(GameLog::getGameId).collect(Collectors.toList());
            List<Game> games = gameService.getAllGames(gameIds);
            List<GameLogDTO> logs = new ArrayList<>();
            for (GameLog log : user.getLogs()) {
                for (Game game: games) {
                    if(log.getGameId().equals(game.getId())){
                        GameLogDTO gameLogDTO = new GameLogDTO();
                        gameLogDTO.setGameLog(log);
                        gameLogDTO.setGame(game);
                        logs.add(gameLogDTO);
                        break;
                    }
                }
            }
            return logs;
        }
        return new ArrayList<>();
    }

    public UserProfilePageDTO getProfilePage(String userId){
        UserProfilePageDTO userProfilePageDTO = new UserProfilePageDTO();
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("There is no user matches with given id: " + userId, HttpStatus.NOT_FOUND.value()));
        userProfilePageDTO.setUser(UserObjectMapper.toReadDTO(user));
        if(user.getLogs() != null && user.getLogs().size() != 0){
            int toIndex = Math.min(user.getLogs().size(),5);
            List<String> gameIds = user.getLogs().stream().sorted(Comparator.comparingLong(GameLog::getStartDate).reversed()).map(GameLog::getGameId).collect(Collectors.toList()).subList(0,toIndex);
            userProfilePageDTO.setRecentlyPlayedGames(gameService.getAllGames(gameIds));
        }
        if(user.getFavoriteGames() != null && user.getFavoriteGames().size() != 0){
            userProfilePageDTO.setFavoriteGames(gameService.getAllGames(user.getFavoriteGames()));
        }

        return userProfilePageDTO;
    }
}
