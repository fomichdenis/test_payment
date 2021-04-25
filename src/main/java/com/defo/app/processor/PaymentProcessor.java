package com.defo.app.processor;

import com.defo.app.model.User;

public interface PaymentProcessor {

    void processPayment(User user);

}
