package com.defo.app.service;

import com.defo.app.model.Account;
import com.defo.app.model.User;
import com.defo.app.model.enums.Currency;
import com.defo.app.repository.AccountRepository;
import com.defo.app.service.interfaces.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.defo.app.utils.Constants.START_AMOUNT;

@Component
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public Account createNewAccountForUser(User user) {
        Account account = repository.findByUserId(user.getUserId());
        if (account == null) {
            account = new Account();
            account.setUserId(user.getUserId());
        }
        account.setBalance(START_AMOUNT);
        account.setCurrency(Currency.USD);
        repository.save(account);
        return account;
    }

    @Override
    public Account getAccountByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public void saveAccount(Account account) {
        repository.save(account);
    }



    /* used for test */
    @Override
    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

}
