package com.gameinn.review.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name="game-service")
public interface GameService{
    @GetMapping("/game/hello")
    String hello();
}
