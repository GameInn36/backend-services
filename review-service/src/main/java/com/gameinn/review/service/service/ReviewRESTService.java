package com.gameinn.review.service.service;

import com.gameinn.review.service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewRESTService {
    private ReviewRepository reviewRepository;
    @Autowired
    ReviewRESTService(ReviewRepository reviewRepository){
    this.reviewRepository = reviewRepository;
    }
}
