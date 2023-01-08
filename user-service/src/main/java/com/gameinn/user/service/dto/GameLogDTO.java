package com.gameinn.user.service.dto;

import com.gameinn.user.service.dataTypes.GameLog;
import com.gameinn.user.service.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameLogDTO {
    private GameLog gameLog;
    private Game game;
}
