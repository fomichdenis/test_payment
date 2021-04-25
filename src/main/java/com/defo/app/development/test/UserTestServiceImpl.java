package com.defo.app.development.test;

import com.defo.app.config.token.TokenProvider;
import com.defo.app.model.User;
import com.defo.app.repository.UserRepository;
import com.defo.app.service.interfaces.AccountService;
import com.defo.app.utils.CustomPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserTestServiceImpl implements  UserTestService {

    Logger log = LoggerFactory.getLogger(UserTestServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CustomPasswordEncoder encoder;

    @Autowired
    private TokenProvider provider;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void addUser(User user) throws InterruptedException {
        User userDb = userRepository.findByUsername(user.getUsername());
        if (userDb == null) {
            user.setPassword(encoder.getEncodedPassword(user));
            userRepository.save(user);
            accountService.createNewAccountForUser(user);
        }
        accountService.createNewAccountForUser(userDb);
    }

}
