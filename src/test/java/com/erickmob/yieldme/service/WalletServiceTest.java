package com.erickmob.yieldme.service;

import com.erickmob.yieldme.model.Role;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.repository.UserRepository;
import com.erickmob.yieldme.repository.WalletRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createWalletForUser() {
//        final User user1 = new User();
//        user1.setUsername("admin");
//        user1.setPassword("asd123");
//        user1.setEmail("admin@yieldme.com");
//        user1.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
//
//        final Wallet wallet = new Wallet();
//        wallet.setUser(user1);
//
//
//        when(walletRepository.save(Mockito.any(Wallet.class))).thenReturn(wallet);
//
//        Wallet created = walletService.createWalletForUser(user1);
//
//        assertThat(created).isNotNull();
//        verify(walletRepository).save(any(Wallet.class));
    }

}