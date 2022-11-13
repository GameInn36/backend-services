package com.gameinn.review.service.service;

import com.gameinn.review.service.dto.ReviewDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.feignClients.GameService;
import com.gameinn.review.service.repository.ReviewRepository;
import com.gameinn.review.service.util.ReviewObjectMapper;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewRESTService {
    private final ReviewRepository reviewRepository;
    private final GameService gameService;
    @Autowired
    ReviewRESTService(ReviewRepository reviewRepository, GameService gameService){
        this.gameService = gameService;
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(ReviewDTO reviewDTO){
        Review newReview = ReviewObjectMapper.toEntity(reviewDTO);
        return reviewRepository.insert(newReview);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public String deneme(){
        return gameService.hello();
    }
}
