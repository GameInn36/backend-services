package com.gameinn.game.service.controller;

import com.gameinn.game.service.dto.DisplayGamesDTO;
import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.dto.GamePageDTO;
import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.exception.GameNotFoundException;
import com.gameinn.game.service.service.GameRESTService;
import com.gameinn.game.service.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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


    @GetMapping("/")
    public List<Game> getAllGames(@RequestParam(required = false) String name,@RequestParam(required = false) String publisher, @RequestParam(required = false) String[] platforms, @RequestParam(required = false) List<String> gameIds)
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
        else if(gameIds != null){
            games = gameRESTService.getAllGamesById(gameIds);
        }
        else{
            games = gameRESTService.getAllGames();
        }
        return games;
    }

    @GetMapping("/{game_id}")
    public Game getGame(@PathVariable("game_id") String gameId) throws GameNotFoundException {
        return gameRESTService.getGame(gameId);
    }

    @GetMapping("/{game_id}/page")
    public GamePageDTO getGamePage(HttpServletRequest request, @PathVariable("game_id") String gameId) throws GameNotFoundException {
        String userId = JwtUtil.getSubject(JwtUtil.getToken(request));
        return gameRESTService.getGamePage(gameId,userId);
    }

    @GetMapping("/displayGames")
    public DisplayGamesDTO displayGames(HttpServletRequest request) throws GameNotFoundException {
        return gameRESTService.getDisplayGamesPage(JwtUtil.getSubject(JwtUtil.getToken(request)));
    }

    @PostMapping("/")
    public Game addGame(@RequestBody GameDTO gameDTO)
    {
        return gameRESTService.addGame(gameDTO);
    }

    @PostMapping("/{gameId}/vote")
    public Game updateVote(@PathVariable String gameId, @RequestBody Game game) throws GameNotFoundException
    {
        return gameRESTService.updateVote(gameId, game.getVote());
    }

    @DeleteMapping("/{game_id}")
    public Game deleteGame(@PathVariable("game_id") String gameId) throws GameNotFoundException {
        return gameRESTService.deleteGame(gameId);
    }

    @PutMapping("/{game_id}")
    public Game updateGame(@PathVariable("game_id") String gameId, @RequestBody GameDTO gameDTO) throws GameNotFoundException {
        return gameRESTService.updateGame(gameId, gameDTO);
    }

    @PutMapping("/{gameId}/increaseLogCount")
    public Game increaseLogCount(@PathVariable String gameId) throws GameNotFoundException {
        return gameRESTService.increaseLogCount(gameId);
    }

    @PutMapping("/{gameId}/decreaseLogCount")
    public Game decreaseLogCount(@PathVariable String gameId) throws GameNotFoundException {
        return gameRESTService.decreaseLogCount(gameId);
    }
}
