package com.salvador.login.dto;

import com.salvador.login.validation.ValidateEmail;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserDto {

    @ValidateEmail (message = "Invalid email.")
    @NotNull
    @NotEmpty (message = "Please provide an email.")
    private String email;

    @NotNull
    @NotEmpty (message = "Please provide your first name.")
    private String firstName;

    @NotNull
    @NotEmpty (message = "Please provide your last name.")
    private String lastName;

//    @NotNull
//    @NotEmpty
//    private String password;
//
//    @NotNull
//    @NotEmpty
//    private String confirmPassword;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public void setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//    }
}
