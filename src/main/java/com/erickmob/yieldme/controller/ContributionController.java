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
    private ModelMapper modelMapper;

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
        Contribution contribution = modelMapper.map(contributionDataDTO, Contribution.class);
        if(!asset.isPresent()){
            throw new CustomException("Asset not found", HttpStatus.NOT_FOUND);
        }
        contribution.setAsset(asset.get());
        return contributionService.save(wallet, contribution);
    }

    // Find
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.findOne}",
            response = ContributionDataDTO.class,
            authorizations = { @Authorization(value="apiKey") }
    )
    Contribution findOne(@PathVariable Long id) {
        return contributionService.findById(id);
    }

    // Save or update
//    @PutMapping("/books/{id}")
//    Book saveOrUpdate(@RequestBody Book newBook, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(x -> {
//                    x.setName(newBook.getName());
//                    x.setAuthor(newBook.getAuthor());
//                    x.setPrice(newBook.getPrice());
//                    return repository.save(x);
//                })
//                .orElseGet(() -> {
//                    newBook.setId(id);
//                    return repository.save(newBook);
//                });
//    }

    // update author only
//    @PatchMapping("/books/{id}")
//    Book patch(@RequestBody Map<String, String> update, @PathVariable Long id) {
//
//        return repository.findById(id)
//                .map(x -> {
//
//                    String author = update.get("author");
//                    if (!StringUtils.isEmpty(author)) {
//                        x.setAuthor(author);
//
//                        // better create a custom method to update a value = :newValue where id = :id
//                        return repository.save(x);
//                    } else {
//                        throw new BookUnSupportedFieldPatchException(update.keySet());
//                    }
//
//                })
//                .orElseGet(() -> {
//                    throw new BookNotFoundException(id);
//                });
//
//    }
//
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
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
