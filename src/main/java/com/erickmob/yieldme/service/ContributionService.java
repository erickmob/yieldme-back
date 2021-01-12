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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Contribution save(Wallet wallet, @Valid Contribution contribution) {
        contribution.setWallet(wallet);
        contribution.setUser(wallet.getUser());
        return contributionRepository.save(contribution);
    }

    public List<Contribution> findAllByUser() {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return contributionRepository.findAllByUser(loggedUser);
    }

    public Contribution findById(Long id) {
        return contributionRepository.findById(id)
                .orElseThrow(() -> new CustomException("Contribution not found with id provided",
                        HttpStatus.NOT_FOUND));
    }


    public Contribution findByIdAndUser(Long id) {
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Contribution> contributionFound = contributionRepository.findByIdAndUser(id, loggedUser);
        if (contributionFound.isEmpty()) {
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
        User loggedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Contribution contribution = createFromDTO(contributionDataDTO);
        if (!asset.isPresent()) {
            throw new CustomException("Asset not found", HttpStatus.NOT_FOUND);
        }
        contribution.setAsset(asset.get());
        contribution.setUser(loggedUser);
        return contribution;
    }

    private Contribution createFromDTO(ContributionDataDTO contributionDataDTO) {
        return Contribution.builder()
                .amount(contributionDataDTO.getAmount())
                .date(contributionDataDTO.getDate())
                .exchange(contributionDataDTO.getExchange())
                .unitPrice(contributionDataDTO.getUnitPrice())
                .totalPrice(contributionDataDTO.getUnitPrice().multiply(contributionDataDTO.getAmount()))
                .build();

    }
}
