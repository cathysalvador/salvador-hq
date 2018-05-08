package com.salvador.user.service;

import com.salvador.user.persistence.model.User;
import com.salvador.user.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/*

 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl (UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    @Override
    public List<User> findAllByOrderByEmailAsc() {
        return userRepository.findAllByOrderByEmailAsc();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByEmail(username);
        if( user == null ){
            throw new UsernameNotFoundException(username);
        }

        return new UserDetailsImpl(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

}
