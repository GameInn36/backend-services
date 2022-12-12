package com.gameinn.game.service.controller;

import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.service.GameRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/game")
public class GameRESTController {
    private final GameRESTService gameRESTService;

    @Autowired
    public GameRESTController(GameRESTService gameRESTService){
        this.gameRESTService = gameRESTService;
    }

    @GetMapping("/hello")
    public String hello(){
        return"Hello from GameService!";
    }

    @GetMapping("/")
    public List<Game> getGames(){
        return gameRESTService.getAllGames();
    }

    /*@GetMapping("/{gameId}")
    public ResponseTemplateVO getGameById(@PathVariable String gameId){
        System.out.println("Controller: " + gameId);
        return gameRESTService.GetGameById(gameId);
    }*/
}
