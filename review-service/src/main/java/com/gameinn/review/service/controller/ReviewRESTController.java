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
    private final ReviewRESTService reviewRESTService;
    @Autowired
    ReviewRESTController(ReviewRESTService reviewRESTService){
        this.reviewRESTService = reviewRESTService;
    }
}
