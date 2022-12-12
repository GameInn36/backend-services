package com.gameinn.game.service.service;

import com.gameinn.game.service.entity.Game;
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

    public List<Game> getAllGames(){
        return gameRepository.findAll();
    }

    /*public ResponseTemplateVO GetGameById(String gameId){
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        responseTemplateVO.setGame(gameRepository.getGameById(gameId));
        responseTemplateVO.setReviews(restTemplate.getForObject("http://REVIEW-SERVICE/review?gameId=" + gameId,ArrayList.class));
        return responseTemplateVO;
    }*/
}
