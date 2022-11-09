package com.gameinn.game.service.repository;

import com.gameinn.game.service.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends MongoRepository<Game,String> {
}
