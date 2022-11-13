package com.gameinn.game.service.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    private String id;
    private String name;
    private int year;
    private Binary cover;
    private String summary;
    private String[] categories;
    private String studio;
    private String[] platforms;
    private float vote;
    private int voteCount;
    private long releaseDate;
}
