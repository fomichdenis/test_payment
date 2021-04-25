package com.defo.app.processor;

import com.defo.app.exception.LowBalanceException;
import com.defo.app.model.Account;
import com.defo.app.model.Payment;
import com.defo.app.model.User;
import com.defo.app.service.interfaces.AccountService;
import com.defo.app.service.interfaces.PaymentService;
import com.defo.app.service.interfaces.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Log4j
@Component
public class PaymentProcessorImpl implements PaymentProcessor {

    private final BigDecimal DECREASE_AMOUNT = new BigDecimal("1.1");

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    PaymentService paymentService;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void processPayment(User user) {
        user = userService.getUserByUsername(user.getUsername());
        Account account = accountService.getAccountByUserId(user.getUserId());
        Payment payment = paymentService.createNewPayment(account, DECREASE_AMOUNT);
        decreaseBalance(account, DECREASE_AMOUNT);
        //accountService.saveAccount(account);
        //log.debug("PaymentProcessorImpl.processPayment works!");
    }

    private void decreaseBalance(Account account, BigDecimal value) {
        BigDecimal balance = account.getBalance();
        if (balance.compareTo(value) < 0) {
            throw new LowBalanceException();
        }
        account.setBalance(balance.subtract(value));
    }

}
