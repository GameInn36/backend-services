package com.gameinn.game.service.controller;

import com.gameinn.game.service.service.GameRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/game")
public class GameRESTController {
    private final GameRESTService gameRESTService;

    @Autowired
    GameRESTController(GameRESTService gameRESTService){
        this.gameRESTService = gameRESTService;
    }

    @GetMapping("/")
    String hello(){
        return"Hello from GameService!";
    }
    /*@GetMapping("/{gameId}")
    public ResponseTemplateVO getGameById(@PathVariable String gameId){
        System.out.println("Controller: " + gameId);
        return gameRESTService.GetGameById(gameId);
    }*/
}
