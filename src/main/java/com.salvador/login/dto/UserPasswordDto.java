package com.salvador.login.dto;

import com.salvador.login.validation.ValidateConfirmPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ValidateConfirmPassword
public class UserPasswordDto {

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
