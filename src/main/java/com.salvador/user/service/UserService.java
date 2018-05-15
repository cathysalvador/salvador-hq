package com.salvador.user.service;

import com.salvador.user.persistence.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findById (Long id);

    User findByEmail (String email);

    User findByConfirmationToken(String confirmationToken);

    List <User> findAllByOrderByEmailAsc();

    User save(User user);

    void delete(User user);

}
