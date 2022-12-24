package com.gameinn.game.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GameLog {
    private Long createDate;
    private Long updateDate;
    private Long startDate;
    private Long stopDate;
    private String gameId;
    private Boolean finished;
}
