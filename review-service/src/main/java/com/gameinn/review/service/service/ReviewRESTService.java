package com.gameinn.review.service.service;

import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.dto.ReviewPageDTO;
import com.gameinn.review.service.dto.ReviewReadDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.exception.ReviewNotFoundException;
import com.gameinn.review.service.exception.ReviewPageException;
import com.gameinn.review.service.feignClient.GameService;
import com.gameinn.review.service.feignClient.UserService;
import com.gameinn.review.service.model.User;
import com.gameinn.review.service.repository.ReviewRepository;
import com.gameinn.review.service.util.ReviewObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.gameinn.review.service.model.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewRESTService {
    private final ReviewRepository reviewRepository;
    private final GameService gameService;
    private final UserService userService;
    @Autowired
    ReviewRESTService(ReviewRepository reviewRepository, GameService gameService, UserService userService){
        this.gameService = gameService;
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }

    public Review addReview(ReviewCreateUpdateDTO reviewCreateUpdateDTO){
        Review newReview = ReviewObjectMapper.toEntity(reviewCreateUpdateDTO);

        String gameId = newReview.getGameId();
        Game gameVoteDTO = new Game();
        gameVoteDTO.setVote(newReview.getVote());
        gameService.updateVote(gameId, gameVoteDTO);
        return reviewRepository.insert(newReview);
    }

    public List<ReviewReadDTO> getAllReviews(){
        List<ReviewReadDTO> reviewReadDTOS = new ArrayList<>();
        List<Review> reviews = reviewRepository.findAll();
        for (Review review: reviews) {
            ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
            reviewReadDTO.setReview(review);
            reviewReadDTO.setGame(gameService.getGame(review.getGameId()));
            reviewReadDTO.setUser(userService.getUserById(review.getUserId()));
            reviewReadDTOS.add(reviewReadDTO);
        }
        return reviewReadDTOS;
    }
    public List<ReviewReadDTO> getReviewsByGameId(String gameId){

        List<ReviewReadDTO> reviewReadDTOS = new ArrayList<>();
        List<Review> reviews = reviewRepository.getReviewsByGameIdOrderByLikeCountDesc(gameId);
        for (Review review: reviews) {
            ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
            reviewReadDTO.setReview(review);
            reviewReadDTO.setGame(gameService.getGame(review.getGameId()));
            reviewReadDTO.setUser(userService.getUserById(review.getUserId()));
            reviewReadDTOS.add(reviewReadDTO);
        }
        return reviewReadDTOS;
    }

    public List<ReviewReadDTO> getReviewsByUserId(String userId){
        List<ReviewReadDTO> reviewReadDTOS = new ArrayList<>();
        List<Review> reviews = reviewRepository.getReviewsByUserIdOrderByLikeCountDesc(userId);
        for (Review review: reviews) {
            ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
            reviewReadDTO.setReview(review);
            reviewReadDTO.setGame(gameService.getGame(review.getGameId()));
            reviewReadDTO.setUser(userService.getUserById(review.getUserId()));
            reviewReadDTOS.add(reviewReadDTO);
        }
        return reviewReadDTOS;
    }

    public List<ReviewReadDTO> getReviewsByUserIdAndGameId(String userId, String gameId){
        List<ReviewReadDTO> reviewReadDTOS = new ArrayList<>();
        List<Review> reviews = reviewRepository.getReviewsByUserIdAndGameIdOrderByLikeCountDesc(userId,gameId);
        for (Review review: reviews) {
            ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
            reviewReadDTO.setReview(review);
            reviewReadDTO.setGame(gameService.getGame(review.getGameId()));
            reviewReadDTO.setUser(userService.getUserById(review.getUserId()));
            reviewReadDTOS.add(reviewReadDTO);
        }
        return reviewReadDTOS;
    }

    public ReviewPageDTO getReviewPage(String userId) throws ReviewPageException {
        User requestOwner = userService.getUserById(userId);
        List<Review> reviews = reviewRepository.findAll().stream().sorted(Comparator.comparingInt(Review::getLikeCount)).sorted(Collections.reverseOrder()).collect(Collectors.toList());
        if(reviews.size() == 0){
            throw new ReviewPageException("There are no reviews!",HttpStatus.NOT_FOUND.value());
        }
        int toIndex = Math.min(reviews.size(), 3);
        List<Review> mostPopularReviews = reviews.subList(0,toIndex);
        ReviewPageDTO reviewPageDTO = new ReviewPageDTO();
        reviewPageDTO.setFriendReviews(new ArrayList<>());
        reviewPageDTO.setMostPopularReviews(new ArrayList<>());
        for (Review review: mostPopularReviews) {
            ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
            reviewReadDTO.setUser(userService.getUserById(review.getUserId()));
            reviewReadDTO.setGame(gameService.getGame(review.getGameId()));
            reviewReadDTO.setReview(review);
            reviewPageDTO.getMostPopularReviews().add(reviewReadDTO);
        }
        if(requestOwner.getFollowing() != null){
            reviews = reviews.stream().filter((review -> requestOwner.getFollowing().contains(review.getUserId()))).sorted(Comparator.comparingLong(Review::getCreatedAt)).sorted(Collections.reverseOrder()).collect(Collectors.toList());
            toIndex = Math.min(reviews.size(), 3);
            List<Review> friendReviews = reviews.subList(0,toIndex);
            for (Review review: friendReviews) {
                ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
                reviewReadDTO.setUser(userService.getUserById(review.getUserId()));
                reviewReadDTO.setGame(gameService.getGame(review.getGameId()));
                reviewReadDTO.setReview(review);
                reviewPageDTO.getFriendReviews().add(reviewReadDTO);
            }
        }

        return reviewPageDTO;
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

    public void likeReview(String userId, String reviewId) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        review.getLikedUsers().add(userId);
        review.setLikeCount(review.getLikedUsers().size());
        reviewRepository.save(review);
    }

    public void unlikeReview(String userId, String reviewId) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        review.getLikedUsers().remove(userId);
        review.setLikeCount(review.getLikedUsers().size());
        reviewRepository.save(review);
    }
}
