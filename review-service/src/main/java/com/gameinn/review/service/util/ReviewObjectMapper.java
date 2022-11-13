package com.gameinn.review.service.util;

import com.gameinn.review.service.dto.ReviewDTO;
import com.gameinn.review.service.entity.Review;

public class ReviewObjectMapper {
    public static Review toEntity(ReviewDTO reviewDTO){
        Review review = new Review();
        review.setGameId(reviewDTO.getGameId());
        review.setUserId(reviewDTO.getUserId());
        review.setContext(reviewDTO.getContext());
        review.setVote(reviewDTO.getVote());
        review.setVoted(reviewDTO.isVoted());
        review.setLikeCount(0);
        review.setCreatedAt(System.currentTimeMillis() / 1000L);
        review.setUpdatedAt(0);
        return review;
    }
}
