package com.salvador.user.service;

import com.salvador.user.persistence.model.User;

public interface UserService {

    public User findByEmail (String email);

    public User findByConfirmationToken(String confirmationToken);

    public User save(User user);

}
