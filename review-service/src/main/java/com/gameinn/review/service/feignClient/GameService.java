package com.gameinn.review.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="game-service", url = "${feign.gameservice.url}")
public interface GameService{
    @GetMapping("/game/hello")
    String hello();
}
