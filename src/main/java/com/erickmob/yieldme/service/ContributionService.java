package com.erickmob.yieldme.service;

import com.erickmob.yieldme.exception.CustomException;
import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    public Contribution save(Wallet wallet, Contribution contribution) {
        contribution.setWallet(wallet);
        return contributionRepository.save(contribution);
    }

    public List<Contribution> findAll(Wallet wallet) {
        return contributionRepository.findAllByWallet(wallet);
    }

    public Contribution findById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Contribution not found with id provided",
                        HttpStatus.NOT_FOUND));
    }

    public void deleteById(Long id) {

    }
}
