package com.erickmob.yieldme.repository;


import com.erickmob.yieldme.model.Role;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.service.AssetService;
import com.erickmob.yieldme.service.ContributionService;
import com.erickmob.yieldme.service.UserService;
import com.erickmob.yieldme.service.WalletService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class WalletRepositoryTestIT {

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
    public void whenFindByUser_thenReturnWallet() {
        // given
//        User user1 = new User();
//        user1.setUsername("admin");
//        user1.setPassword("asd123");
//        user1.setEmail("admin@yieldme.com");
//        user1.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
//
//        entityManager.persist(user1);
//        entityManager.flush();
//
//        Wallet wallet = new Wallet();
//        wallet.setUser(user1);
//        entityManager.persist(wallet);
//        entityManager.flush();
//
//        // when
//        Wallet found = walletRepository.findByUser(user1);
//
//        // then
//        assertThat(found.getUser().getUsername())
//                .isEqualTo(user1.getUsername());
    }
}