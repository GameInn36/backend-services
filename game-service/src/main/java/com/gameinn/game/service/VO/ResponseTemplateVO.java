package com.gameinn.game.service.VO;

import com.gameinn.game.service.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateVO {
    private Game game;
    private ArrayList<Review> reviews;
}
