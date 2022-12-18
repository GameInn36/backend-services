package com.gameinn.game.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "review-service", url = "${feign.reviewservice.url}")
public interface ReviewService {

}
