package com.erickmob.yieldme.repository;

import com.erickmob.yieldme.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contribution, Integer> {


}