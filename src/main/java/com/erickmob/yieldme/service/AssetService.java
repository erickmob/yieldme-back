package com.erickmob.yieldme.service;

import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    public Asset save(Asset asset) {
        return assetRepository.save(asset);
    }

}
