package com.gameinn.game.service.service;

import com.gameinn.game.service.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class GameRESTService {
    private final GameRepository gameRepository;
    private final RestTemplate restTemplate;
    @Autowired
    GameRESTService(GameRepository gameRepository, RestTemplate restTemplate){
        this.gameRepository = gameRepository;
        this.restTemplate = restTemplate;
    }


    /*public ResponseTemplateVO GetGameById(String gameId){
        ResponseTemplateVO responseTemplateVO = new ResponseTemplateVO();
        responseTemplateVO.setGame(gameRepository.getGameById(gameId));
        responseTemplateVO.setReviews(restTemplate.getForObject("http://REVIEW-SERVICE/review?gameId=" + gameId,ArrayList.class));
        return responseTemplateVO;
    }*/
}
