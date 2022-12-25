package com.gameinn.user.service.feignClient;

import com.gameinn.user.service.model.Game;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="game-service", url = "${feign.gameservice.url}")
public interface GameService{
    @GetMapping("/game/")
    List<Game> getAllGames();

    @GetMapping("/game/")
    List<Game> getAllGames(@RequestParam List<String> gameIds);

    @PutMapping("/{gameId}/increaseLogCount")
    void increaseLogCount(String gameId);
}
