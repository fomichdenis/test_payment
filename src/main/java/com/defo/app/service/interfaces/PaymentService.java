package com.defo.app.service.interfaces;

import com.defo.app.model.Account;
import com.defo.app.model.Payment;
import com.defo.app.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {

    Payment createNewPayment(Account account, BigDecimal amount);

    /* used for test */
    List<Payment> getAllPayments();
}
