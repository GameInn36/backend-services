package com.gameinn.review.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "game-service",url = "${feign.gameService.url}")
public interface GameService{
    @GetMapping("/")
    String hello();
}
