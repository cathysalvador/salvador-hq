package com.salvador.user.service;

import com.salvador.user.persistence.model.User;

import java.util.List;

public interface UserService {

    User findByEmail (String email);

    User findByConfirmationToken(String confirmationToken);

    List <User> findAllByOrderByEmailAsc();

    User save(User user);

}
