package com.gameinn.review.service.util;

import com.gameinn.review.service.dto.ReviewCreateUpdateDTO;
import com.gameinn.review.service.entity.Review;

public class ReviewObjectMapper {
    public static Review toEntity(ReviewCreateUpdateDTO reviewCreateUpdateDTO){
        Review review = new Review();
        review.setGameId(reviewCreateUpdateDTO.getGameId());
        review.setUserId(reviewCreateUpdateDTO.getUserId());
        review.setContext(reviewCreateUpdateDTO.getContext());
        review.setVote(reviewCreateUpdateDTO.getVote());
        review.setVoted(reviewCreateUpdateDTO.isVoted());
        review.setLikeCount(0);
        review.setCreatedAt(System.currentTimeMillis() / 1000L);
        review.setUpdatedAt(0);
        return review;
    }
}
