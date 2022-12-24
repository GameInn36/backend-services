package com.gameinn.game.service.dto;

import com.gameinn.game.service.entity.Game;
import com.gameinn.game.service.model.GameLog;
import com.gameinn.game.service.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameLogDTO {
    private GameLog log;
    private User user;
    private Game game;
}
