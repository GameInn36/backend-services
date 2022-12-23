package com.gameinn.game.service.dto;

import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.model.GamePageReview;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GamePageDTO {
    private Game game;
    private List<GamePageReview> reviews;
    private List<GamePageReview> followedFriendsReviews;
}
