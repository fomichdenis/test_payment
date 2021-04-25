package com.defo.app.service;

import com.defo.app.model.User;
import com.defo.app.repository.UserRepository;
import com.defo.app.service.interfaces.AccountService;
import com.defo.app.service.interfaces.UserService;
import com.defo.app.utils.CustomPasswordEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private CustomPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;


    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        return userOpt.orElse(null);
    }

    @Override
    public User findByLoginAndPassword(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            log.debug("username = {}, password = {}", user.getUsername(), user.getPassword());
            if (encoder.matches(username, password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }


    /* used for test */
    @Override
    public void addUser(User user) {
        user.setPassword(encoder.getEncodedPassword(user));
        userRepository.save(user);
        accountService.createNewAccountForUser(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
