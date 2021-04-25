package com.defo.app.utils;

import com.defo.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {

    @Autowired
    PasswordEncoder passwordEncoder;

    private final static String SALT = "$$&%";

    public String getEncodedPassword(User user) {
        return getEncodedPassword(user.getUsername(), user.getPassword());
    }

    public String getEncodedPassword(String username, String password) {
        return passwordEncoder.encode(getRawPassword(username, password));
    }

    public boolean matches(String username, String password, String dbUserPassword) {
        return passwordEncoder.matches(getRawPassword(username, password), dbUserPassword);
    }

    private String getRawPassword(String username, String password) {
        return username + password + SALT;
    }
}
