package com.gameinn.review.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "GAME-SERVICE",url = "${feign.gameservice.url}")
public interface GameService{
    @GetMapping("/")
    String hello();
}
