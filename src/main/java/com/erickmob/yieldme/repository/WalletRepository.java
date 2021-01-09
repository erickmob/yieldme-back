package com.erickmob.yieldme.repository;

import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Wallet findByUser(User user);

}