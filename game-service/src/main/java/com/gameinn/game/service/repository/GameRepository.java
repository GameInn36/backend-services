package com.gameinn.game.service.repository;

import com.gameinn.game.service.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository<Game> extends MongoRepository<Game,String> {
}
