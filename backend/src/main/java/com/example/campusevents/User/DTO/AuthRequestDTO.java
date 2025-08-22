package com.example.campusevents.User.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequestDTO {

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;


    @NotBlank
    @Size(min = 6)
    private String password;


    public @NotBlank @Size(min = 3, max = 20) String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank @Size(min = 3, max = 20) String username) {
        this.username = username;
    }



    public @NotBlank @Size(min = 6) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6) String password) {
        this.password = password;
    }
}
