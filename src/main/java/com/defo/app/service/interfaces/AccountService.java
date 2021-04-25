package com.defo.app.service.interfaces;

import com.defo.app.model.Account;
import com.defo.app.model.User;

import java.util.List;

public interface AccountService {

    Account createNewAccountForUser(User user);

    Account getAccountByUserId(Long userId);

    void saveAccount(Account account);

    /* used for test */
    List<Account> getAllAccounts();
}
