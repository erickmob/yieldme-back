package com.erickmob.yieldme.controller;

import com.erickmob.yieldme.dto.ContributionDataDTO;
import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.model.Contribution;
import com.erickmob.yieldme.service.AssetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assets")
@Api(tags = "assets")
public class AssetController {

    @Autowired
    private AssetService assetService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @ApiOperation(value = "${ContributionsController.findAll}",
            response = ContributionDataDTO.class,
            responseContainer = "List",
            authorizations = {@Authorization(value = "apiKey")})
    List<Asset> findAll(@RequestParam String query) {
        return assetService.findAllByTickerStartsWith(query);
    }


}
