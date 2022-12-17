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
        if(src.getEmail() != null){
            dest.setEmail(src.getEmail());
        }
        if(src.getPassword() != null){
            dest.setPassword(src.getPassword());
        }
        if(src.getUsername() != null){
            dest.setUsername(src.getUsername());
        }
        if(src.getBio() != null){
            dest.setBio(src.getBio());
        }
        if(src.getProfileImage() != null){
            dest.setProfileImage(src.getProfileImage());
        }
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
                .setFollowedFriends(user.getFollowedFriends())
                .setProfileImage(user.getProfileImage())
                .setToPlayList(user.getToPlayList())
                .build();
    }
}
