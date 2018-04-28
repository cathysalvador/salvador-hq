package com.salvador.user.persistence.repository;

import com.salvador.user.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

    User findByEmail (String email);

    User findByConfirmationToken(String confirmationToken);

}
