package com.gameinn.game.service.service;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.dto.GamePageDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.feignClient.ReviewService;
import com.gameinn.game.service.feignClient.UserService;
import com.gameinn.game.service.model.GamePageReview;
import com.gameinn.game.service.model.Review;
import com.gameinn.game.service.repository.GameRepository;
import com.gameinn.game.service.util.GameObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        for (Review review:reviews) {
            GamePageReview gamePageReview = new GamePageReview.Builder()
                    .setUser(userService.getUserById(review.getUserId()))
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

    public Game updateVote(String gameId, float vote) throws GameNotFoundException
    {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));

        int addedVoteCount = vote >= 0 ? 1 : -1;

        int gameVoteCount = game.getVoteCount();
        float gameVote = game.getVote();

        int newGameVoteCount = gameVoteCount + addedVoteCount;
        float newVote;

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
}
