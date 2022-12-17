package com.gameinn.game.service.util;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;

public class GameObjectMapper {
    public static Game toEntity(GameDTO gameDTO){
        return new Game.GameBuilder(gameDTO.getName())
                .setCategories(gameDTO.getCategories())
                .setPlatforms(gameDTO.getPlatforms())
                .setYear(gameDTO.getYear())
                .setReleaseDate(gameDTO.getReleaseDate())
                .setStudio(gameDTO.getStudio())
                .setPlatforms(gameDTO.getPlatforms())
                .setCover(gameDTO.getCover())
                .build();
    }
}
