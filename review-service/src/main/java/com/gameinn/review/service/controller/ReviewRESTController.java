package com.gameinn.review.service.controller;
import com.gameinn.review.service.dto.ReviewDTO;
import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.service.ReviewRESTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<Review> getAllReviews(){
        return this.reviewRESTService.getAllReviews();
    }
    @PostMapping("/")
    public Review addReview(@RequestBody @Valid ReviewDTO reviewDTO){
        /*Set<ConstraintViolation<ReviewDTO>> violations = validator.validate(reviewDTO);
        for (ConstraintViolation<ReviewDTO> violation : violations) {
            System.out.println(violation.getMessage());
        }*/

        return this.reviewRESTService.addReview(reviewDTO);
    }

    @GetMapping("/deneme")
    public String deneme(){
        return reviewRESTService.deneme();
    }
}
