package com.gameinn.user.service.dto;

import com.gameinn.user.service.dataTypes.GameLog;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateUpdateDTO {

    @NotNull
    @Size(min = 5, max = 25, message = "Username should be min 5 max 25 chars")
    private String username;
    @NotNull
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Password must contain at least one digit [0-9], one lowercase Latin character [a-z], " +
                    "one uppercase Latin character [A-Z], one special character like ! @ # & ( ), " +
                    "a length of at least 8 characters and a maximum of 20 characters. ")
    @NotNull
    private String password;
    private String bio;
    @NotNull
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    private List<String> favoriteGames;
    private List<String> toPlayList;
    private List<GameLog> logs;
    private String profileImage;
}
