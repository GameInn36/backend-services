package com.gameinn.user.service.entity;

import com.gameinn.user.service.dataTypes.GameLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String userName;
    private String password;
    private String bio;
    @Indexed(unique = true)
    private String email;
    private List<String> toPlayList;
    private List<String> followedFriends;
    private List<String> favoriteGames;
    private List<GameLog> logs;
    private Binary profileImage;

    private User(UserBuilder userBuilder){
        this.id = userBuilder.id;
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.bio = userBuilder.bio;
        this.email = userBuilder.email;
        this.toPlayList = userBuilder.toPlayList;
        this.followedFriends = userBuilder.followedFriends;
        this.favoriteGames = userBuilder.favoriteGames;
        this.logs = userBuilder.logs;
        this.profileImage = userBuilder.profileImage;
    }

    public static class UserBuilder{
        private String id;
        private final String userName;
        private final String password;
        private String bio;
        private final String email;
        private List<String> toPlayList;
        private List<String> followedFriends;
        private List<String> favoriteGames;
        private List<GameLog> logs;
        private Binary profileImage;

        public UserBuilder(String userName, String password, String email){
            this.userName = userName;
            this.password = password;
            this.email = email;
        }

        public UserBuilder setId(String id){
            this.id = id;
            return this;
        }
        public UserBuilder setBio(String bio) {
            this.bio = bio;
            return this;
        }

        public UserBuilder setToPlayList(List<String> toPlayList) {
            this.toPlayList = toPlayList;
            return this;
        }

        public UserBuilder setFollowedFriends(List<String> followedFriends) {
            this.followedFriends = followedFriends;
            return this;
        }

        public UserBuilder setLogs(List<GameLog> logs) {
            this.logs = logs;
            return this;
        }

        public UserBuilder setProfileImage(Binary profileImage) {
            this.profileImage = profileImage;
            return this;
        }

        public UserBuilder setFavoriteGames(List<String> favoriteGames) {
            this.favoriteGames = favoriteGames;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
