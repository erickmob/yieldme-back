package com.erickmob.yieldme.repository;

import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {

    List<Contribution> findAllByWallet(Wallet wallet);

}