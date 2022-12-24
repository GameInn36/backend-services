package com.gameinn.game.service.dto;

import com.gameinn.game.service.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class DisplayGamesDTO {
    private List<Game> newGames;
    private List<Game> mostPopularGames;
    private List<GameLogDTO> newsFromFriends;

    public DisplayGamesDTO(){
        this.mostPopularGames = new ArrayList<>();
        this.newsFromFriends = new ArrayList<>();
        this.newGames = new ArrayList<>();
    }
}
