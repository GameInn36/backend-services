package com.gameinn.review.service.service;

import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.exception.ReviewNotFoundException;
import com.gameinn.review.service.feignClient.GameService;
import com.gameinn.review.service.repository.ReviewRepository;
import com.gameinn.review.service.util.ReviewObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.gameinn.review.service.model.Game;

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

        String gameId = newReview.getGameId();
        Game gameVoteDTO = new Game();
        gameVoteDTO.setVote(newReview.getVote());
        gameService.updateVote(gameId, gameVoteDTO);
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

    public Review deleteReview(String reviewId) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        Game gameVoteDTO = new Game();
        gameVoteDTO.setVote(-review.getVote());
        gameService.updateVote(review.getGameId(), gameVoteDTO);
        reviewRepository.deleteById(reviewId);
        return review;
    }
}
