package com.erickmob.yieldme.service;

import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }


    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    public List<Asset> findAllByTickerStartsWith(String query) {
        return assetRepository.findAllByTickerStartsWith(query);
    }

    public Optional<Asset> findById(Long assetId) {
        return assetRepository.findById(assetId);
    }
}
