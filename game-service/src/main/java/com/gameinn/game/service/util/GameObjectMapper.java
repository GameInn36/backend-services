package com.gameinn.game.service.util;

import com.gameinn.game.service.dto.GameDTO;
import com.gameinn.game.service.entity.Game;

public class GameObjectMapper {
    public static Game toEntity(GameDTO gameDTO){
        return new Game.GameBuilder(gameDTO.getName())
                .setGenres(gameDTO.getGenres())
                .setPlatforms(gameDTO.getPlatforms())
                .setFirst_release_date(gameDTO.getFirst_release_date())
                .setPlatforms(gameDTO.getPlatforms())
                .setCover(gameDTO.getCover())
                .setPublisher(gameDTO.getPublisher())
                .setSummary(gameDTO.getSummary())
                .setLogCount(gameDTO.getLogCount())
                .build();
    }

    public static Game mapGame(GameDTO src, Game dest){
        dest.setName(src.getName());
        dest.setCover(src.getCover());
        dest.setSummary(src.getSummary());
        dest.setGenres(src.getGenres());
        dest.setPublisher(src.getPublisher());
        dest.setPlatforms(src.getPlatforms());
        dest.setFirst_release_date(src.getFirst_release_date());
        dest.setLogCount(src.getLogCount());
        return dest;
    }
}
