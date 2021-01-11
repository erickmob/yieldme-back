package com.erickmob.yieldme.service;

import com.erickmob.yieldme.dto.ContributionDataDTO;
import com.erickmob.yieldme.exception.CustomException;
import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.repository.ContributionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@Validated
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Contribution save(Wallet wallet, @Valid Contribution contribution) {
        contribution.setWallet(wallet);
//        contribution.setUser(wallet.getUser());
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


    public Contribution findByIdAndUser(Long id, User user) {
        Optional<Contribution> contributionFound = contributionRepository.findByIdAndUser(id, user);
        if(contributionFound.isEmpty()){
            throw new CustomException("Contribution not found with id provided",
                    HttpStatus.NOT_FOUND);
        }
        return contributionFound.get();
    }


    public void deleteById(Long id) {

    }

    public Contribution update(Contribution contributionDataDTO, Contribution contribution) {
        contribution.setAsset(contributionDataDTO.getAsset());
        contribution.setDate(contributionDataDTO.getDate());
        contribution.setAmount(contributionDataDTO.getAmount());
        contribution.setUnitPrice(contributionDataDTO.getUnitPrice());
        contribution.setTotalPrice(contributionDataDTO.getTotalPrice());
        return contributionRepository.save(contribution);
    }

    public Contribution contributionWithAssetFromDTO(ContributionDataDTO contributionDataDTO, Optional<Asset> asset) {
        Contribution contribution = modelMapper.map(contributionDataDTO, Contribution.class);
        if(!asset.isPresent()){
            throw new CustomException("Asset not found", HttpStatus.NOT_FOUND);
        }
        contribution.setAsset(asset.get());
        return contribution;
    }
}
