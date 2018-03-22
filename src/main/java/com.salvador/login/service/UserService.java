package com.salvador.login.service;

import com.salvador.login.domain.User;

public interface UserService {

    public User findByEmail (String email);

    public User findByUsername (String username);

}
