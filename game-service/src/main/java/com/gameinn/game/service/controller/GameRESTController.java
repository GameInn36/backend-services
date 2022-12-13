package com.gameinn.game.service.controller;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.service.GameRESTService;
import org.hibernate.validator.constraints.Range;
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
    public List<Game> getAllGames()
    {
        return gameRESTService.getAllGames();
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
