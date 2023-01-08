package com.gameinn.user.service.dto;

import com.gameinn.user.service.dataTypes.GameLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {
    @NotNull
    @Size(min = 5, max = 25, message = "Username should be min 5 max 25 chars")
    private String username;
    @NotNull
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    private String bio;
    private List<String> favoriteGames;
    private List<String> toPlayList;
    private List<GameLog> logs;
    private String profileImage;
}
