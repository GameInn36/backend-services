package com.gameinn.authentication.service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String username;
    private String bio;
    private String email;
    private List<String> toPlayList;
    private List<String> following;
    private List<String> followers;
    private List<String> favoriteGames;
    private List<GameLog> logs;
    private String profileImage;
}

