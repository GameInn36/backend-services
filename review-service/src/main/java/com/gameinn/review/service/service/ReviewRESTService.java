package com.gameinn.review.service.service;

import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.dto.ReviewPageDTO;
import com.gameinn.review.service.dto.ReviewReadDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.exception.ReviewLikeUnlikeException;
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

    public List<ReviewReadDTO> getReviewsByUserIdAsReviewReadDTO(String userId){
        List<Review> userReviews = reviewRepository.getReviewsByUserIdOrderByLikeCountDesc(userId);
        List<String> gameIds = userReviews.stream().map(Review::getGameId).collect(Collectors.toList());
        List<Game> games = gameService.getAllGamesByGameId(gameIds);
        List<ReviewReadDTO> result = new ArrayList<>();
        for (Review review: userReviews) {
            ReviewReadDTO reviewReadDTO = new ReviewReadDTO();
            reviewReadDTO.setReview(review);
            reviewReadDTO.setGame(games.stream().filter((game)-> game.getId().equals(review.getGameId())).findFirst().get());
            reviewReadDTO.setUser(null);
            result.add(reviewReadDTO);
        }
        return result;
    }

    public ReviewPageDTO getReviewPage(String userId) throws ReviewPageException {
        User requestOwner = userService.getUserById(userId);
        List<Review> reviews = reviewRepository.findAll().stream().sorted(Comparator.comparingInt(Review::getLikeCount).reversed()).collect(Collectors.toList());
        if(reviews.size() == 0){
            throw new ReviewPageException("There are no reviews!",HttpStatus.NOT_FOUND.value());
        }
        int toIndex = Math.min(reviews.size(), 5);
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
            reviews = reviews.stream().filter((review -> requestOwner.getFollowing().contains(review.getUserId()))).sorted(Comparator.comparingLong(Review::getCreatedAt).reversed()).collect(Collectors.toList());
            toIndex = Math.min(reviews.size(), 5);
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

    public Review updateReview(String reviewId, ReviewCreateUpdateDTO reviewCreateUpdateDTO) throws ReviewNotFoundException {
        Review updatedReview = ReviewObjectMapper.toEntity(reviewCreateUpdateDTO);
        Review oldReview = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        updatedReview.setCreatedAt(oldReview.getCreatedAt());
        updatedReview.setUpdatedAt(System.currentTimeMillis() / 1000L);
        updatedReview.setLikedUsers(oldReview.getLikedUsers());
        updatedReview.setLikeCount(oldReview.getLikeCount());
        updatedReview.setId(oldReview.getId());
        Game updateVoteGame = new Game();
        updateVoteGame.setVote(-oldReview.getVote());
        gameService.updateVote(oldReview.getGameId(),updateVoteGame);
        updateVoteGame.setVote(updatedReview.getVote());
        gameService.updateVote(updatedReview.getGameId(),updateVoteGame);
        reviewRepository.save(updatedReview);
        return updatedReview;
    }

    public Review deleteReview(String reviewId, boolean r) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        if(r){
            Game gameVoteDTO = new Game();
            gameVoteDTO.setVote(-review.getVote());
            gameService.updateVote(review.getGameId(), gameVoteDTO);
        }
        reviewRepository.deleteById(reviewId);
        return review;
    }

    public List<Review> deleteReviewsByUserId(String userId){
        List<Review> reviews = reviewRepository.getReviewsByUserIdOrderByLikeCountDesc(userId);
        for (Review review: reviews) {
            Game gameVoteDTO = new Game();
            gameVoteDTO.setVote(-review.getVote());
            gameService.updateVote(review.getGameId(), gameVoteDTO);
            reviewRepository.deleteById(review.getId());
        }
        return reviews;
    }

    public void likeReview(String userId, String reviewId) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        if(review.getLikedUsers() == null){
            review.setLikedUsers(new ArrayList<>());
        }
        review.getLikedUsers().add(userId);
        review.setLikeCount(review.getLikedUsers().size());
        reviewRepository.save(review);
    }

    public void unlikeReview(String userId, String reviewId) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(()-> new ReviewNotFoundException("There is no review with given id: "+reviewId, HttpStatus.NOT_FOUND.value()));
        if(review.getLikedUsers() == null || review.getLikedUsers().size() == 0){
            throw new ReviewLikeUnlikeException("Empty liked users list", HttpStatus.NOT_ACCEPTABLE.value());
        }
        if(!review.getLikedUsers().contains(userId)){
            throw new ReviewLikeUnlikeException("The user did not like the review before",HttpStatus.NOT_ACCEPTABLE.value());
        }
        review.getLikedUsers().remove(userId);
        review.setLikeCount(review.getLikedUsers().size());
        reviewRepository.save(review);
    }
}
