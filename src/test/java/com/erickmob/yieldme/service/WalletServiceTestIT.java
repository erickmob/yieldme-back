package com.erickmob.yieldme.service;
import com.erickmob.yieldme.model.*;
import com.erickmob.yieldme.repository.WalletRepository;
import com.erickmob.yieldme.service.AssetService;
import com.erickmob.yieldme.service.ContributionService;
import com.erickmob.yieldme.service.UserService;
import com.erickmob.yieldme.service.WalletService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WalletServiceTestIT {


    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private WalletRepository walletRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private WalletService walletService;

    @MockBean
    private ContributionService contributionService;

    @MockBean
    private AssetService assetService;

    @Test
    public void whenCreateWallet_thenReturnContributionsSize3() {
        // given USER
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("asd123");
        user1.setEmail("admin@yieldme.com");
        user1.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

        entityManager.persist(user1);
        entityManager.flush();

        // given Wallet
        Wallet wallet = new Wallet();
        wallet.setUser(user1);
        entityManager.persist(wallet);
        entityManager.flush();

        // given Assets
        Asset xplg = new Asset("XPLG11", "XPLG11", AssetCategory.FII, Currency.REAIS);
        try {
            entityManager.persist(xplg);
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Asset vt = new Asset("VT", "VT", AssetCategory.ETF, Currency.DOLLAR);
        try {
            entityManager.persist(vt);
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // given Contributions
        Contribution contribution1 = new Contribution(
                Exchanges.XP, LocalDate.now(),
                xplg,
                new BigDecimal(0.50001),
                new BigDecimal(1234.1001),
                new BigDecimal(617.062391)
                ,wallet
        );

        Contribution contribution2 = new Contribution(
                Exchanges.XP, LocalDate.now(),
                vt,
                new BigDecimal(0.50001),
                new BigDecimal(1234.1001),
                new BigDecimal(617.062391)
                ,wallet
        );

        entityManager.persist(contribution1);
        entityManager.flush();

        entityManager.persist(contribution2);
        entityManager.flush();

        wallet.getContributions().add(contribution1);
        wallet.getContributions().add(contribution2);
        entityManager.persist(wallet);
        entityManager.flush();


        // when
        Wallet found = walletRepository.findByUser(user1);
        Assert.assertNotNull(found);
        Assert.assertEquals(2, found.getContributions().size());

    }

}