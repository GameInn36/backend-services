package com.gameinn.game.service.service;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.feignClient.ReviewService;
import com.gameinn.game.service.repository.GameRepository;
import com.gameinn.game.service.util.GameObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRESTService {
    private final GameRepository gameRepository;

    private final ReviewService reviewService;
    @Autowired
    GameRESTService(GameRepository gameRepository, ReviewService reviewService){
        this.gameRepository = gameRepository;
        this.reviewService = reviewService;
    }

    public void addGame(GameDTO gameDTO)
    {
        gameRepository.insert(GameObjectMapper.toEntity(gameDTO));
    }

    public Game getGame(String gameId) throws GameNotFoundException
    {
        return gameRepository.findById(gameId).orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));
    }

    public List<Game> getAllGames()
    {
        return gameRepository.findAll();
    }

    public Game deleteGame(String gameId) throws GameNotFoundException {
        Game game = gameRepository.findById(gameId).orElseThrow(()-> new GameNotFoundException("There is no game with given id: "+gameId, HttpStatus.NOT_FOUND.value()));
        gameRepository.delete(game);
        return game;
        //TODO reviews will be also deleted
    }

    public void addVote(String gameId, int vote)
    {
        Game game = gameRepository.findById(gameId).orElseThrow(RuntimeException::new); //TODO change exception
        float currentVote = game.getVote();
        int currentVoteCount = game.getVoteCount();
        int newVoteCount = currentVoteCount + 1;
        game.setVoteCount(newVoteCount);
        float floatVote = (float) vote;
        float newVote = ((currentVote * currentVoteCount) + floatVote) / newVoteCount;
        game.setVote(newVote);
        gameRepository.save(game);
    }

    public List<Game> getGamesByStudio(String studio){
        return gameRepository.findByStudioStartingWithIgnoreCase(studio);
    }

    public List<Game> getGamesByPlatform(String[] platforms){
        return gameRepository.findByPlatformsContainingIgnoreCase(platforms);
    }

    public List<Game> getGamesByName(String name){
        return gameRepository.findByNameStartingWithIgnoreCaseOrderByNameAsc(name);
    }

    /*public ResponseTemplateVO GetGameById(String gameId){
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        responseTemplateVO.setGame(gameRepository.getGameById(gameId));
        responseTemplateVO.setReviews(restTemplate.getForObject("http://REVIEW-SERVICE/review?gameId=" + gameId,ArrayList.class));
        return responseTemplateVO;
    }*/
}
