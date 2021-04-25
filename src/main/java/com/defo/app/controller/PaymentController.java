package com.defo.app.controller;

import com.defo.app.exception.LowBalanceException;
import com.defo.app.model.User;
import com.defo.app.model.auth.ErrorResponse;
import com.defo.app.processor.PaymentProcessor;
import com.defo.app.utils.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    @Autowired
    private PaymentProcessor processor;

    @Autowired
    private AuthenticationHelper authHelper;

    @PostMapping("/payment")
    public ResponseEntity payment() {
        System.out.println("payment works!");
        User user = authHelper.getCurrentUser();
        try {
            processor.processPayment(user);
        }
        catch (LowBalanceException ex) {
            return new ResponseEntity<>(new ErrorResponse("Too low balance"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
