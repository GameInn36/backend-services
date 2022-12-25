package com.gameinn.game.service.service;

import com.gameinn.game.service.dto.DisplayGamesDTO;
import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.dto.GameLogDTO;
import com.gameinn.game.service.dto.GamePageDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.feignClient.ReviewService;
import com.gameinn.game.service.feignClient.UserService;
import com.gameinn.game.service.model.GameLog;
import com.gameinn.game.service.model.GamePageReview;
import com.gameinn.game.service.model.Review;
import com.gameinn.game.service.model.User;
import com.gameinn.game.service.repository.GameRepository;
import com.gameinn.game.service.util.GameObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GameRESTService {
    private final GameRepository gameRepository;
    private final ReviewService reviewService;
    private final UserService userService;
    @Autowired
    GameRESTService(GameRepository gameRepository, ReviewService reviewService, UserService userService){
        this.gameRepository = gameRepository;
        this.reviewService = reviewService;
        this.userService = userService;
    }

    public void addGame(GameDTO gameDTO)
    {
        gameRepository.insert(GameObjectMapper.toEntity(gameDTO));
    }

    public Game getGame(String gameId) throws GameNotFoundException {
        return gameRepository.findById(gameId)
                .orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));
    }

    public List<Game> getAllGames()
    {
        return gameRepository.findAll();
    }

    public List<Game> getAllGamesById(List<String> gameIds){
        return (List<Game>) gameRepository.findAllById(gameIds);
    }

    public Game deleteGame(String gameId) throws GameNotFoundException {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));
        gameRepository.delete(game);
        return game;
        //TODO reviews will be also deleted
    }

    public Game updateGame(String gameId, GameDTO gameDTO) throws GameNotFoundException {
        Game updatedGame = GameObjectMapper.mapGame(gameDTO,gameRepository.findById(gameId).orElseThrow(()-> new GameNotFoundException("Game not found with given id: "+ gameId,HttpStatus.NOT_FOUND.value())));
        return gameRepository.save(updatedGame);
    }

    public List<Game> getGamesByPublisher(String publisher){
        return gameRepository.findByPublisherStartingWithIgnoreCaseOrderByNameAsc(publisher);
    }

    public List<Game> getGamesByPlatform(String[] platforms){
        return gameRepository.findByPlatformsContainingIgnoreCase(platforms);
    }

    public List<Game> getGamesByName(String name){
        return gameRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(name);
    }

    public GamePageDTO getGamePage(String gameId, String userId) throws GameNotFoundException {
        GamePageDTO gamePage = new GamePageDTO();
        gamePage.setGame(getGame(gameId));
        gamePage.setReviews(new ArrayList<>());
        List<Review> reviews = reviewService.getReviewsByGameId(gameId);
        List<String> userIds = reviews.stream().map(Review::getUserId).collect(Collectors.toList());
        List<User> users = userService.getAllUsers(userIds);
        for (Review review:reviews) {
            GamePageReview gamePageReview = new GamePageReview.Builder()
                    .setUser(users.stream().filter((user)-> user.getId().equals(review.getUserId())).findFirst().orElseThrow(RuntimeException::new))
                    .setId(review.getId())
                    .setContext(review.getContext())
                    .setCreatedAt(review.getCreatedAt())
                    .setUpdatedAt(review.getUpdatedAt())
                    .setVote(review.getVote())
                    .setVoted(review.isVoted())
                    .setLikeCount(review.getLikeCount())
                    .build();
            gamePage.getReviews().add(gamePageReview);
        }
        List<String> friends = userService.getUserById(userId).getFollowing();
        if(friends != null){
            List<GamePageReview> followedFriendsReviews = gamePage.getReviews().stream().filter((review -> friends.contains(review.getUser().getId())))
                    .collect(Collectors.toList());
            gamePage.setFollowedFriendsReviews(followedFriendsReviews);
        }
        else{
            gamePage.setFollowedFriendsReviews(new ArrayList<>());
        }
        return gamePage;
    }

    public DisplayGamesDTO getDisplayGamesPage(String userId) throws GameNotFoundException {
        DisplayGamesDTO displayGamesDTO = new DisplayGamesDTO();
        List<Game> games = gameRepository.findAll().stream().sorted(Comparator.comparingLong(Game::getFirst_release_date)).collect(Collectors.toList());
        displayGamesDTO.setNewGames(games.subList(0,5));
        displayGamesDTO.setMostPopularGames(games.stream().sorted(Comparator.comparingDouble(Game::getVote).reversed()).collect(Collectors.toList()).subList(0,5));
        User requestOwner = userService.getUserById(userId);
        if(requestOwner.getFollowing() != null && requestOwner.getFollowing().size() != 0){
            List<User> friends = userService.getAllUsers(requestOwner.getFollowing());
            List<GameLogDTO> friendLogs = new ArrayList<>();
            for (User user: friends) {
                if(user.getLogs() != null && user.getLogs().size() != 0){
                    GameLogDTO gameLogDTO = new GameLogDTO();
                    GameLog log = user.getLogs().stream().sorted(Comparator.comparingLong(GameLog::getCreateDate)).collect(Collectors.toList()).get(0);
                    gameLogDTO.setLog(log);
                    gameLogDTO.setUser(user);
                    gameLogDTO.setGame(gameRepository.findById(log.getGameId()).orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+log.getGameId(), HttpStatus.NOT_FOUND.value())));
                    friendLogs.add(gameLogDTO);
                }
            }
            displayGamesDTO.setNewsFromFriends(friendLogs);
        }
        return displayGamesDTO;
    }

    public Game updateVote(String gameId, double vote) throws GameNotFoundException
    {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));

        int addedVoteCount = vote >= 0 ? 1 : -1;

        int gameVoteCount = game.getVoteCount();
        double gameVote = game.getVote();

        int newGameVoteCount = gameVoteCount + addedVoteCount;
        double newVote;

        if(newGameVoteCount == 0)
        {
            newVote = 0;
        }
        else
        {
            newVote = ((gameVote * gameVoteCount) + vote) / newGameVoteCount;
        }

        game.setVote(newVote);
        game.setVoteCount(newGameVoteCount);

        return gameRepository.save(game);
    }

    public void increaseLogCount(String gameId) throws GameNotFoundException {
        Game game = gameRepository.findById(gameId).orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));
        game.setLogCount(game.getLogCount()+1);
        gameRepository.save(game);
    }
}
