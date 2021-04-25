package com.defo.app.development.test;

import com.defo.app.model.User;

import java.util.List;

public interface UserTestService {

    void addUser(User user) throws InterruptedException;

    List<User> getAllUsers();
}
