package com.salvador.login.repository;

import com.salvador.login.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long> {

    User findByEmail (String email);

    User findByConfirmationToken(String confirmationToken);

}
