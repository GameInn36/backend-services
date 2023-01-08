package com.gameinn.user.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private String id;
    private String name;
    private String cover;
    private String summary;
    private List<String> genres;
    private String publisher;
    private List<String> platforms;
    private float vote;
    private int voteCount;
    private long first_release_date;
    private int logCount;
}
