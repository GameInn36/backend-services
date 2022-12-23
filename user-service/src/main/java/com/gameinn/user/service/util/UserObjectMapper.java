package com.gameinn.user.service.util;

import com.gameinn.user.service.dto.UserCreateUpdateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.entity.User;

public class UserObjectMapper {
    public static User toEntity(UserCreateUpdateDTO user){
        return new User.UserBuilder(user.getUsername(),user.getPassword(),user.getEmail())
                .setBio(user.getBio())
                .setProfileImage(user.getProfileImage())
                .build();
    }

    public static User mapUser(UserCreateUpdateDTO src, User dest){
        dest.setEmail(src.getEmail());
        dest.setPassword(src.getPassword());
        dest.setUsername(src.getUsername());
        dest.setBio(src.getBio());
        dest.setProfileImage(src.getProfileImage());
        dest.setFavoriteGames(src.getFavoriteGames());
        dest.setToPlayList(src.getToPlayList());
        return dest;
    }
    public static UserReadDTO toReadDTO(User user){
        return new UserReadDTO.UserReadDTOBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setBio(user.getBio())
                .setUsername(user.getUsername())
                .setFavoriteGames(user.getFavoriteGames())
                .setLogs(user.getLogs())
                .setFollowing(user.getFollowing())
                .setFollowers(user.getFollowers())
                .setProfileImage(user.getProfileImage())
                .setToPlayList(user.getToPlayList())
                .build();
    }
}
