package com.gameinn.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDTO {
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Password must contain at least one digit [0-9], one lowercase Latin character [a-z], " +
                    "one uppercase Latin character [A-Z], one special character like ! @ # & ( ), " +
                    "a length of at least 8 characters and a maximum of 20 characters. ")
    @NotNull
    private String password;
}
