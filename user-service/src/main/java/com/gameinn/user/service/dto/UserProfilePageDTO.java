package com.gameinn.user.service.dto;

import com.gameinn.user.service.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePageDTO {
    UserReadDTO user;
    List<Game> favoriteGames;
    List<Game> recentlyPlayedGames;
}
