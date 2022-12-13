package com.gameinn.game.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private String name;
    private int year;
    private String cover;
    private String summary;
    private String[] categories;
    private String studio;
    private String[] platforms;
    private long releaseDate;
}
