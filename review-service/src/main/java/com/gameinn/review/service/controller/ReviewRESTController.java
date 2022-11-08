package com.gameinn.review.service.controller;


import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.service.ReviewRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewRESTController {
    @Autowired
    private ReviewRESTService reviewRESTService;
    @GetMapping("/")
    public ArrayList<Review> getReviews()
    {
        return reviewRESTService.getReviews();
    }

    @GetMapping
    public List<Review> getReviewsByGameId(@RequestParam("gameId") String gameId){
        System.out.println("Controller: gameId=" + gameId);
        return reviewRESTService.getReviewsByGameId(gameId);
    }

    @PostMapping("/")
    public String addReview(@RequestBody Review review){
        System.out.println("Controller: " +review.toString());
        return reviewRESTService.addReview(review) ? "Success" : "Fail";
    }
}
