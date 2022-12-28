package com.gameinn.review.service.controller;
import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.dto.ReviewPageDTO;
import com.gameinn.review.service.dto.ReviewReadDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.exception.ImproperReviewException;
import com.gameinn.review.service.exception.ReviewNotFoundException;
import com.gameinn.review.service.exception.ReviewPageException;
import com.gameinn.review.service.service.ReviewRESTService;
import com.gameinn.review.service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/review")
public class ReviewRESTController {
    private final ReviewRESTService reviewRESTService;
    private final Validator validator;

    @Autowired
    ReviewRESTController(ReviewRESTService reviewRESTService){
        this.reviewRESTService = reviewRESTService;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @GetMapping("/")
    public List<Review> getAllReviews(@RequestParam(required = false) String userId, @RequestParam(required = false) String gameId){
        if(gameId != null && userId != null){
            return this.reviewRESTService.getReviewsByUserIdAndGameId(userId,gameId);
        }
        else if(userId != null){
            return this.reviewRESTService.getReviewsByUserId(userId);
        }
        else if(gameId != null){
            return this.reviewRESTService.getReviewsByGameId(gameId);
        }
        return this.reviewRESTService.getAllReviews();
    }

    @GetMapping("/reviewsPage")
    public List<ReviewReadDTO> getReviewsByUserId(@RequestParam String userId){
        return reviewRESTService.getReviewsByUserIdAsReviewReadDTO(userId);
    }

    @GetMapping("/displayReviews")
    public ReviewPageDTO getReviewPage(HttpServletRequest request) throws ReviewPageException {
        String userId = JwtUtil.getSubject(JwtUtil.getToken(request));
        return reviewRESTService.getReviewPage(userId);
    }
    @PostMapping("/")
    public Review addReview(@RequestBody ReviewCreateUpdateDTO reviewCreateUpdateDTO){
        Set<ConstraintViolation<ReviewCreateUpdateDTO>> violations = validator.validate(reviewCreateUpdateDTO);
        if(!violations.isEmpty()){
            throw new ImproperReviewException(violations.stream().findFirst().get().getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
        }
        return this.reviewRESTService.addReview(reviewCreateUpdateDTO);
    }

    @PostMapping("/{reviewId}/like")
    public void likeReview(HttpServletRequest request, @PathVariable String reviewId) throws ReviewNotFoundException {
        reviewRESTService.likeReview(JwtUtil.getSubject(JwtUtil.getToken(request)),reviewId);
    }

    @PostMapping("/{reviewId}/unlike")
    public void unlikeReview(HttpServletRequest request, @PathVariable String reviewId) throws ReviewNotFoundException {
        reviewRESTService.unlikeReview(JwtUtil.getSubject(JwtUtil.getToken(request)),reviewId);
    }

    @PutMapping("/{reviewId}")
    public Review updateReview(@PathVariable String reviewId, @RequestBody ReviewCreateUpdateDTO reviewCreateUpdateDTO) throws ReviewNotFoundException {
        Set<ConstraintViolation<ReviewCreateUpdateDTO>> violations = validator.validate(reviewCreateUpdateDTO);
        if(!violations.isEmpty()){
            throw new ImproperReviewException(violations.stream().findFirst().get().getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
        }
        return reviewRESTService.updateReview(reviewId,reviewCreateUpdateDTO);
    }

    @DeleteMapping("/{review_id}")
    public Review deleteReview(@PathVariable("review_id") String reviewId) throws ReviewNotFoundException {
        return reviewRESTService.deleteReview(reviewId);
    }
}
