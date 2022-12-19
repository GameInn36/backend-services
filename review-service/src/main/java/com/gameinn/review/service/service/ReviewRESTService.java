package com.gameinn.review.service.service;

import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.feignClient.GameService;
import com.gameinn.review.service.repository.ReviewRepository;
import com.gameinn.review.service.util.ReviewObjectMapper;
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

    public Review addReview(ReviewCreateUpdateDTO reviewCreateUpdateDTO){
        Review newReview = ReviewObjectMapper.toEntity(reviewCreateUpdateDTO);
        return reviewRepository.insert(newReview);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }
    public List<Review> getReviewsByGameId(String gameId){
        return reviewRepository.getReviewsByGameIdOrderByLikeCountDesc(gameId);
    }

    public List<Review> getReviewsByUserId(String userId){
        return reviewRepository.getReviewsByUserIdOrderByLikeCountDesc(userId);
    }

    public List<Review> getReviewsByUserIdAndGameId(String userId, String gameId){
        return reviewRepository.getReviewsByUserIdAndGameIdOrderByLikeCountDesc(userId,gameId);
    }
    public String deneme(){
        return gameService.hello();
    }
}
