package com.gameinn.game.service.repository;

import com.gameinn.game.service.entity.Game;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Optional;

@Repository
public class GameRepository {
    private ArrayList<Game> gameList;
    @PostConstruct
    public void loadList() {
        gameList = new ArrayList<>();
    }
    public boolean addGame(Game game){
        gameList.add(game);
        return true;
    }

    public ArrayList<Game> getGames()
    {
        return gameList;
    }

    public Game getGameById(String gameId){
        return gameList.stream().filter(game -> game.getId().equals(gameId)).findFirst().get();
    }
}
