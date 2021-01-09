package com.erickmob.yieldme.repository;

import com.erickmob.yieldme.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {


}