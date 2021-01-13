package com.erickmob.yieldme.controller;

import com.erickmob.yieldme.dto.ContributionDataDTO;
import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.service.AssetService;
import com.erickmob.yieldme.service.ContributionService;
import com.erickmob.yieldme.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contributions")
@Api(tags = "contributions")
public class ContributionController {

    @Autowired
    private ContributionService contributionService;

    @Autowired
    private WalletService walletService;

    @Autowired
    private AssetService assetService;


    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.findAll}",
            response = ContributionDataDTO.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "apiKey")})
    List<Contribution> findAll() {
        return contributionService.findAllByUser();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.findOne}",
            response = ContributionDataDTO.class,
            authorizations = {@Authorization(value = "apiKey")}
    )
    Contribution findOne(@PathVariable Long id) {

        return contributionService.findByIdAndUser(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.create}",
            response = Contribution.class,
            authorizations = {@Authorization(value = "apiKey")}
    )
    Contribution save(@RequestBody ContributionDataDTO contributionDataDTO) {

        Wallet wallet = walletService.findByUser();
        Optional<Asset> asset = assetService.findById(contributionDataDTO.getAsset().getId());
        Contribution contribution = contributionService.contributionWithAssetFromDTO(contributionDataDTO, asset);

        return contributionService.save(wallet, contribution);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.update}",
            response = Contribution.class,
            authorizations = {@Authorization(value = "apiKey")}
    )
    Contribution update(HttpServletRequest req,
                        @RequestBody ContributionDataDTO contributionDataDTO,
                        @PathVariable Long id) {
        //TODO: testar:
        // - user not found
        // - contribution not found by id and user
        // - asset not found
        // - contributionFromDTO
        // - update
        Contribution contribution = contributionService.findByIdAndUser(id);
        Optional<Asset> asset = assetService.findById(contributionDataDTO.getAsset().getId());
        Contribution contributionFromDTO = contributionService.contributionWithAssetFromDTO(contributionDataDTO, asset);
        return contributionService.update(contributionFromDTO, contribution);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${ContributionsController.delete}",
            authorizations = {@Authorization(value = "apiKey")}
    )
    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Long id) {
        contributionService.deleteById(id);
    }

}
