package com.gameinn.user.service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name="review-service", url = "${feign.reviewservice.url}")
public interface ReviewService{
   @DeleteMapping("/")
    void deleteReviewsByUserId(@RequestParam String userId);
}
