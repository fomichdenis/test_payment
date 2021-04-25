package com.defo.app.service.interfaces;

import com.defo.app.model.User;

import java.util.List;

public interface UserService {

    User getUserByUsername(String username);

    User getUserById(Long user);

    User findByLoginAndPassword(String username, String password);


    /* used for test */
    void addUser(User user);

    List<User> getAllUsers();

}
