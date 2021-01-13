package com.erickmob.yieldme.repository;

import com.erickmob.yieldme.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Long> {


    List<Asset> findAllByTickerStartsWith(String query);
}