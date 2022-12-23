package com.gameinn.review.service.controller;
import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.dto.ReviewPageDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.exception.ReviewNotFoundException;
import com.gameinn.review.service.exception.ReviewPageException;
import com.gameinn.review.service.service.ReviewRESTService;
import com.gameinn.review.service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.*;
import java.util.List;

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

    @GetMapping("/reviewPage")
    public ReviewPageDTO getReviewPage(HttpServletRequest request) throws ReviewPageException {
        String userId = JwtUtil.getSubject(JwtUtil.getToken(request));
        return reviewRESTService.getReviewPage(userId);
    }
    @PostMapping("/")
    public Review addReview(@RequestBody @Valid ReviewCreateUpdateDTO reviewCreateUpdateDTO){
        /*Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(reviewDTO);
        for (ConstraintViolation<ReviewDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }*/

        return this.reviewRESTService.addReview(reviewCreateUpdateDTO);
    }

    @DeleteMapping("/{review_id}")
    public Review deleteReview(@PathVariable("review_id") String reviewId) throws ReviewNotFoundException {
        return reviewRESTService.deleteReview(reviewId);
    }
}
