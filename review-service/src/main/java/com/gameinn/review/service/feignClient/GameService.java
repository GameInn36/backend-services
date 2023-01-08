package com.gameinn.review.service.feignClient;

import com.gameinn.review.service.model.Game;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name="game-service", url = "${feign.gameservice.url}")
public interface GameService{

    @GetMapping("/game/")
    List<Game> getAllGamesByGameId(@RequestParam List<String> gameIds);

    @GetMapping("/game/{gameId}")
    Game getGame(@PathVariable String gameId);

    @PutMapping("/game/{gameId}")
    Game updateGameVote(@PathVariable String gameId, @RequestBody Game gameDTO);

    @PostMapping("/game/{gameId}/vote")
    void updateVote(@PathVariable String gameId, @RequestBody Game game);
}
