package com.gameinn.user.service.entity;

import com.gameinn.user.service.dataTypes.GameLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    private String userName;
    private String bio;
    private String mail;
    private List<String> toPlayList;
    private List<String> followedFriends;
    private List<String> favoriteGames;
    private List<GameLog> logs;
    private Binary profileImage;

}
