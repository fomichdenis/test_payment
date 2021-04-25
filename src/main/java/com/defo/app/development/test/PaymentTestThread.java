package com.defo.app.development.test;

import com.defo.app.model.User;
import com.defo.app.processor.PaymentProcessor;
import lombok.SneakyThrows;

public class PaymentTestThread extends Thread {
    private final PaymentProcessor processor;
    private final User name;

    PaymentTestThread(PaymentProcessor processor, User name) {
        this.name = name;
        this.processor = processor;
    }
    @SneakyThrows
    @Override
    public void run() {
        processor.processPayment(name);
    }
}
