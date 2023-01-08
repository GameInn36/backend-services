package com.gameinn.game.service.feignClient;

import com.gameinn.game.service.model.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "review-service", url = "${feign.reviewservice.url}")
public interface ReviewService {
    @GetMapping("/review/")
    List<Review> getReviewsByGameId(@RequestParam String gameId);

    @DeleteMapping("/review/{reviewId}")
    Review deleteReview(@PathVariable String reviewId, @RequestParam boolean r);

}
