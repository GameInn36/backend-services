package com.gameinn.review.service.dto;

import com.gameinn.review.service.entity.Review;
import com.gameinn.review.service.model.Game;
import com.gameinn.review.service.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReadDTO {
    private Review review;
    private Game game;
    private User user;
    private List<String> likedUsers;
}
