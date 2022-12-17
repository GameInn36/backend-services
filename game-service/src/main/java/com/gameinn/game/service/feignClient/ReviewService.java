package com.gameinn.game.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "review-service")
public interface ReviewService {

}
