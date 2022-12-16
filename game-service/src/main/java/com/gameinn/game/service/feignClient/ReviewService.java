package com.gameinn.game.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "REVIEW-SERVICE",url = "${feign.reviewservice.url}")
public interface ReviewService {

}
