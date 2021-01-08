package com.erickmob.yieldme.service;

import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet createWalletForUser(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    public Wallet findByUser(User user){
        return walletRepository.findByUser(user);
    }

    public Wallet save(Wallet wallet){
        return walletRepository.save(wallet);
    }

}
