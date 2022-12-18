package com.gameinn.game.service.controller;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.service.GameRESTService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
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

    /*@GetMapping("/{gameId}")
    public ResponseTemplateVO getGameById(@PathVariable String gameId){
        System.out.println("Controller: " + gameId);
        return gameRESTService.GetGameById(gameId);
    }*/

    @PostMapping("/")
    public void addGame(@RequestBody GameDTO gameDTO)
    {
        gameRESTService.addGame(gameDTO);
    }

    @GetMapping("/")
    public List<Game> getAllGames(@RequestParam(required = false) String name,@RequestParam(required = false) String publisher, @RequestParam(required = false) String[] platforms)
    {
        List<Game> games;
        if(name!= null){
            games = gameRESTService.getGamesByName(name);
        }
        else if(publisher != null){
            games = gameRESTService.getGamesByPublisher(publisher);
        }
        else if(platforms != null){
            games = gameRESTService.getGamesByPlatform(platforms);
        }
        else{
            games = gameRESTService.getAllGames();
        }
        return games;
    }

    @GetMapping("/{game_id}")
    public Game getGame(@PathVariable("game_id") String gameId) throws GameNotFoundException
    {
        return gameRESTService.getGame(gameId);
    }

    @DeleteMapping("/{game_id}")
    public Game deleteGame(@PathVariable("game_id") String gameId) throws GameNotFoundException {
        return gameRESTService.deleteGame(gameId);
    }

    @PutMapping("/{game_id}")
    public void updateGameVote(@PathVariable("game_id") String gameId, @RequestBody @Range(min = 0, max = 5) int vote)
    {
        gameRESTService.addVote(gameId, vote);
    }
}
