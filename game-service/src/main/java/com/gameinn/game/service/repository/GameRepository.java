package com.gameinn.game.service.repository;

import com.gameinn.game.service.entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game,String> {

    List<Game> findByPlatformsContainingIgnoreCase(String[] platforms);
    List<Game> findByPublisherStartingWithIgnoreCaseOrderByNameAsc(String publisher);
    List<Game> findByNameStartingWithIgnoreCaseOrderByNameAsc(String name);
}
