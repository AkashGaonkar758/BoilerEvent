package com.example.campusevents.User.DTO;
import com.example.campusevents.User.Model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {



    private String firstName;

    private String lastName;
    @Email
    private String email;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private User.Year year;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User.Year getYear() {
        return year;
    }

    public void setYear(User.Year year) {
        this.year = year;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }
}
