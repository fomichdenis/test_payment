package com.defo.app.repository;

import com.defo.app.model.Account;
import com.defo.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUserId(Long userId);
}
