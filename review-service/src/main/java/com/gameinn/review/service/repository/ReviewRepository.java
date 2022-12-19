package com.gameinn.review.service.repository;

import com.gameinn.review.service.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review,String> {
    List<Review> getReviewsByGameIdOrderByLikeCountDesc(String gameId);
    List<Review> getReviewsByUserIdOrderByLikeCountDesc(String userId);
    List<Review> getReviewsByUserIdAndGameIdOrderByLikeCountDesc(String userId, String gameId);
}
