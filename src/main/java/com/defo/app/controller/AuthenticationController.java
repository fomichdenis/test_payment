package com.defo.app.controller;

import com.defo.app.config.login.LoginHelper;
import com.defo.app.config.token.TokenProvider;
import com.defo.app.model.User;
import com.defo.app.model.auth.AuthRequest;
import com.defo.app.model.auth.AuthResponse;
import com.defo.app.model.auth.ErrorResponse;
import com.defo.app.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private LoginHelper loginHelper;

    @PostMapping("/auth")
    public ResponseEntity login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        if (loginHelper.isBlocked(request)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Too many login attempts. Try again in 15 minutes"),
                    HttpStatus.BAD_REQUEST
            );
        }

        User user = userService.findByLoginAndPassword(authRequest.getLogin(), authRequest.getPassword());
        if (user == null) {
            loginHelper.registerFailedAttempt(request);
            return new ResponseEntity<>(
                    new ErrorResponse("Login or/and password are not correct"),
                    HttpStatus.UNAUTHORIZED
            );
        }
        loginHelper.registerSuccessAttempt(request);

        String token = tokenProvider.generateTokenForUser(user);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    @PostMapping("/process_logout")
    public ResponseEntity logout(HttpServletRequest request) {
        tokenProvider.releaseToken(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
