package com.gameinn.game.service.controller;

import com.gameinn.game.service.VO.ResponseTemplateVO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.service.GameRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@RestController
@RequestMapping("/game")
public class GameRESTController {
    private final GameRESTService gameRESTService;

    @Autowired
    GameRESTController(GameRESTService gameRESTService){
        this.gameRESTService = gameRESTService;
    }

    /*@GetMapping("/{gameId}")
    public ResponseTemplateVO getGameById(@PathVariable String gameId){
        System.out.println("Controller: " + gameId);
        return gameRESTService.GetGameById(gameId);
    }*/
}
