package com.gameinn.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private String name;
    private String cover;
    private String summary;
    private List<String> genres;
    private String publisher;
    private List<String> platforms;
    private long first_release_date;
}
