package com.gameinn.user.service.util;

import com.gameinn.user.service.dto.UserCreateDTO;
import com.gameinn.user.service.dto.UserReadDTO;
import com.gameinn.user.service.dto.UserUpdateDTO;
import com.gameinn.user.service.entity.User;

public class UserObjectMapper {
    public static User toEntity(UserCreateDTO user){
        return new User.UserBuilder(user.getUsername(),user.getPassword(),user.getEmail())
                .build();
    }
    public static User mapUserFromUpdateDTO(UserUpdateDTO src, User dest){
        dest.setEmail(src.getEmail());
        dest.setUsername(src.getUsername());
        dest.setBio(src.getBio());
        dest.setLogs(src.getLogs());
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
