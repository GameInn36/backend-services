package com.gameinn.game.service.util;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;

public class GameObjectMapper {

    //TODO: setCover yok resim işine karar ver
    public static Game toEntity(GameDTO gameDTO){
        return new Game.GameBuilder(gameDTO.getName())
                .setCategories(gameDTO.getCategories())
                .setPlatforms(gameDTO.getPlatforms())
                .setYear(gameDTO.getYear())
                .setReleaseDate(gameDTO.getReleaseDate())
                .setStudio(gameDTO.getStudio())
                .setPlatforms(gameDTO.getPlatforms())
                .build();
    }
}
