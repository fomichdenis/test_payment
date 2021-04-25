package com.defo.app.development.test;

import com.defo.app.model.Account;
import com.defo.app.model.Payment;
import com.defo.app.model.User;
import com.defo.app.processor.PaymentProcessor;
import com.defo.app.service.interfaces.AccountService;
import com.defo.app.service.interfaces.PaymentService;
import com.defo.app.utils.AuthenticationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/development_test")
public class DevelopmentTestController {

    @Autowired
    UserTestService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    private PaymentProcessor processor;

    @Autowired
    private AuthenticationHelper authHelper;

    @PostMapping("add_user")
    public void addUser(@RequestBody User user) throws InterruptedException {
        userService.addUser(user);
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

    @GetMapping("get_users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("get_accounts")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("get_payments")
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @PostMapping("several_payments")
    public void severalPayments() {
        User user = authHelper.getCurrentUser();
        Thread[] threads = new PaymentTestThread[7];
        for (Thread thread : threads) {
            thread = new Thread(() -> processor.processPayment(user));
            thread.start();
        }
    }
}
