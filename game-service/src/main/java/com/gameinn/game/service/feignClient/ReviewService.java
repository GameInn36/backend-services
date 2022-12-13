package com.gameinn.game.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "review-service",url = "${feign.reviewService.url}")
public interface ReviewService {

}
