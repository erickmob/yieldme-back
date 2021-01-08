package com.erickmob.yieldme.service;

import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    public Contribution save(Contribution contribution) {
        return contributionRepository.save(contribution);
    }

}
