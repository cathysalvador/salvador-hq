package com.salvador.login.service;

import com.salvador.login.persistence.model.User;

public interface UserService {

    public User findByEmail (String email);

    public User findByConfirmationToken(String confirmationToken);

    public User save(User user);

}
