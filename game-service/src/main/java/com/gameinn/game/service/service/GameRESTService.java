package com.gameinn.game.service.service;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRESTService {
    private final GameRepository gameRepository;
    @Autowired
    GameRESTService(GameRepository gameRepository){
        this.gameRepository = gameRepository;
    }

    public void addGame(GameDTO gameDTO)
    {
        Game game = new Game();
        game.setName(gameDTO.getName());
        game.setYear(gameDTO.getYear());
        //TODO Convert string binary encoding to binary
        game.setSummary(gameDTO.getSummary());
        game.setCategories(gameDTO.getCategories());
        game.setStudio(gameDTO.getStudio());
        game.setPlatforms(gameDTO.getPlatforms());
        game.setVote(0);
        game.setVoteCount(0);
        game.setReleaseDate(gameDTO.getReleaseDate());
        gameRepository.insert(game);
    }

    public Game getGame(String gameId) throws GameNotFoundException
    {
        return gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new); //TODO change exception
    }

    public List<Game> getAllGames()
    {
        return gameRepository.findAll();
    }

    public Game deleteGame(String gameId) throws GameNotFoundException {
        Game game = gameRepository.findById(gameId).orElseThrow(GameNotFoundException::new); //TODO change exception
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


    /*public ResponseTemplateVO GetGameById(String gameId){
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        responseTemplateVO.setGame(gameRepository.getGameById(gameId));
        responseTemplateVO.setReviews(restTemplate.getForObject("http://REVIEW-SERVICE/review?gameId=" + gameId,ArrayList.class));
        return responseTemplateVO;
    }*/
}
