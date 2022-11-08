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
    @Autowired
    private GameRESTService gameRESTService;
    @GetMapping("/")
    public ArrayList<Game> getGames(){
        return gameRESTService.getGames();
    }

    @GetMapping("/{gameId}")
    public ResponseTemplateVO getGameById(@PathVariable String gameId){
        System.out.println("Controller: " + gameId);
        return gameRESTService.GetGameById(gameId);
    }

    @PostMapping("/")
    public String addGame(@RequestBody Game game){
        if (gameRESTService.addGame(game)){
            return "Game Successfully Added!";
        }
        else{
            return "An error occurred while adding game.";
        }
    }
}
