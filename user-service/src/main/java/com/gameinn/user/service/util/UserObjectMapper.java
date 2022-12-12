package com.gameinn.user.service.util;

import com.gameinn.user.service.dto.UserDTO;
import com.gameinn.user.service.entity.User;

public class UserObjectMapper {
    public static User toEntity(UserDTO user){
        return new User.UserBuilder(user.getUserName(),user.getPassword(),user.getEmail())
                .setBio(user.getBio())
                .setLogs(user.getLogs())
                .setFavoriteGames(user.getFavoriteGames())
                .setFollowedFriends(user.getFollowedFriends())
                .setProfileImage(user.getProfileImage())
                .setToPlayList(user.getToPlayList())
                .build();
    }
}
