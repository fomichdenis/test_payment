package com.defo.app.service;

import com.defo.app.model.Account;
import com.defo.app.model.Payment;
import com.defo.app.repository.PaymentRepository;
import com.defo.app.service.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createNewPayment(Account account, BigDecimal amount) {
        Payment payment = new Payment();
        payment.setAccountId(account.getAccountId());
        payment.setAmount(amount);
        payment.setBalanceBefore(account.getBalance());
        payment.setBalanceAfter(account.getBalance().subtract(amount));
        payment.setDate((new Date()).toString());
        paymentRepository.save(payment);
        return payment;
    }

    /* used for test */
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
