package com.erickmob.yieldme.controller;

import com.erickmob.yieldme.dto.ContributionDataDTO;
import com.erickmob.yieldme.dto.UserResponseDTO;
import com.erickmob.yieldme.exception.CustomException;
import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.model.Wallet;
import com.erickmob.yieldme.repository.ContributionRepository;
import com.erickmob.yieldme.repository.UserRepository;
import com.erickmob.yieldme.security.JwtTokenProvider;
import com.erickmob.yieldme.service.AssetService;
import com.erickmob.yieldme.service.ContributionService;
import com.erickmob.yieldme.service.UserService;
import com.erickmob.yieldme.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.sql.Connection;
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
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AssetService assetService;


    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.findAll}",
            response = ContributionDataDTO.class,
            responseContainer="List",
            authorizations = { @Authorization(value="apiKey") })
    List<Contribution> findAll(HttpServletRequest req) {
        Wallet wallet = getWalletFromRequest(req);
        return contributionService.findAll(wallet);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.findOne}",
            response = ContributionDataDTO.class,
            authorizations = { @Authorization(value="apiKey") }
    )
    Contribution findOne(Principal principal, @PathVariable Long id) {
        return contributionService.findById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.create}",
            response = Contribution.class,
            authorizations = { @Authorization(value="apiKey") }
    )
    Contribution save(HttpServletRequest req,
                      @RequestBody ContributionDataDTO contributionDataDTO) {

        Wallet wallet = getWalletFromRequest(req);
        Optional<Asset> asset = assetService.findById(contributionDataDTO.getAssetId());
        Contribution contribution = contributionService.contributionWithAssetFromDTO(contributionDataDTO, asset);

        return contributionService.save(wallet, contribution);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.update}",
            response = Contribution.class,
            authorizations = { @Authorization(value="apiKey") }
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
        User user = userService.getUserFromReq(req);
        Contribution contribution = contributionService.findByIdAndUser(id, user);
        Optional<Asset> asset = assetService.findById(contributionDataDTO.getAssetId());
        Contribution contributionFromDTO = contributionService.contributionWithAssetFromDTO(contributionDataDTO, asset);

        return contributionService.update(contributionFromDTO, contribution);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${ContributionsController.delete}",
            authorizations = { @Authorization(value="apiKey") }
    )
    @DeleteMapping("/{id}")
    void deleteBook(@PathVariable Long id) {
        contributionService.deleteById(id);
    }

    private Wallet getWalletFromRequest(HttpServletRequest req) throws CustomException{
        User user = userService.getUserFromReq(req);
        return walletService.findByUser(user);
    }
}
