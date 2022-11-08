package com.gameinn.review.service.service;

import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewRESTService {
    @Autowired
    private ReviewRepository reviewRepository;

    public ArrayList<Review> getReviews(){
        return reviewRepository.getReviewArrayList();
    }

    public boolean addReview(Review review){
        System.out.println("Service: " + review.toString());
        return reviewRepository.addReview(review);
    }

    public List<Review> getReviewsByGameId(String gameId){ return reviewRepository.getReviewsByGameId(gameId); }
}
