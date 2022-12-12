package com.gameinn.user.service.dto;

import com.gameinn.user.service.dataTypes.GameLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;

import javax.validation.constraints.Email;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String userName;
    private String password;
    private String bio;
    @Email
    private String email;
    private List<String> toPlayList;
    private List<String> followedFriends;
    private List<String> favoriteGames;
    private List<GameLog> logs;
    private Binary profileImage;
}
