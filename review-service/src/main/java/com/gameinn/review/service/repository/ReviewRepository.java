package com.gameinn.review.service.repository;

import com.gameinn.review.service.entity.Review;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ReviewRepository {
    private ArrayList<Review> reviewArrayList;

    @PostConstruct
    public void loadList() {
        reviewArrayList = new ArrayList<>();
    }

    public ArrayList<Review> getReviewArrayList(){
        return reviewArrayList;
    }

    public List<Review> getReviewsByGameId(String gameId){
        return reviewArrayList.stream().filter(review -> review.getGameId().equals(gameId)).collect(Collectors.toList());
    }

    public boolean addReview(Review review) {
        System.out.println("Repository: " + review.toString());
        reviewArrayList.add(review);
        return true;
    }
}
