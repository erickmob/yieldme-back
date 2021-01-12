package com.erickmob.yieldme.service;

import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Wallet createWalletForUser(User user) {
        Wallet wallet = new Wallet();
        wallet.setUser(user);
        return walletRepository.save(wallet);
    }

    public Wallet findByUser(){
        User loggedUser  =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return walletRepository.findByUser(loggedUser);
    }

    public Wallet save(Wallet wallet){
        return walletRepository.save(wallet);
    }

}
