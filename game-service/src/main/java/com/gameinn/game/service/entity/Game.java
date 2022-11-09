package com.gameinn.game.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String id;
    private String name;
    /*private String info;
    private String category;
    private String studio;
    private ArrayList<String> platforms;
    private float vote;
    private int voteCount;
    private String image;*/
}
