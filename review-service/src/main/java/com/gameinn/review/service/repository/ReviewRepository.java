package com.gameinn.review.service.repository;

import com.gameinn.review.service.entity.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review,String> {
}
