package com.salvador.login.persistence.repository;

import com.salvador.login.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

    User findByEmail (String email);

    User findByConfirmationToken(String confirmationToken);

}
