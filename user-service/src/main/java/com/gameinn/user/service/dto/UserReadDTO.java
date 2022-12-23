package com.gameinn.user.service.dto;

import com.gameinn.user.service.dataTypes.GameLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReadDTO {
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

    private UserReadDTO(UserReadDTOBuilder userReadDTOBuilder){
        this.id = userReadDTOBuilder.id;
        this.username = userReadDTOBuilder.username;
        this.bio = userReadDTOBuilder.bio;
        this.email = userReadDTOBuilder.email;
        this.toPlayList = userReadDTOBuilder.toPlayList;
        this.followers = userReadDTOBuilder.followers;
        this.following = userReadDTOBuilder.following;
        this.favoriteGames = userReadDTOBuilder.favoriteGames;
        this.logs = userReadDTOBuilder.logs;
        this.profileImage = userReadDTOBuilder.profileImage;
    }

    public static class UserReadDTOBuilder{
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

        public UserReadDTOBuilder(){}

        public UserReadDTOBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public UserReadDTOBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserReadDTOBuilder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public UserReadDTOBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserReadDTOBuilder setToPlayList(List<String> toPlayList) {
            this.toPlayList = toPlayList;
            return this;
        }

        public UserReadDTOBuilder setFollowers(List<String> followers) {
            this.followers = followers;
            return this;
        }

        public UserReadDTOBuilder setFollowing(List<String> following) {
            this.following = following;
            return this;
        }

        public UserReadDTOBuilder setFavoriteGames(List<String> favoriteGames) {
            this.favoriteGames = favoriteGames;
            return this;
        }

        public UserReadDTOBuilder setLogs(List<GameLog> logs) {
            this.logs = logs;
            return this;
        }

        public UserReadDTOBuilder setProfileImage(String profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public UserReadDTO build(){
            return new UserReadDTO(this);
        }
    }
}