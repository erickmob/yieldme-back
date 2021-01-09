package com.erickmob.yieldme;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import com.erickmob.yieldme.model.*;
import com.erickmob.yieldme.repository.AssetRepository;
import com.erickmob.yieldme.repository.ContributionRepository;
import com.erickmob.yieldme.repository.WalletRepository;
import com.erickmob.yieldme.service.AssetService;
import com.erickmob.yieldme.service.ContributionService;
import com.erickmob.yieldme.service.UserService;
import com.erickmob.yieldme.service.WalletService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class YieldMeApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	@Autowired
	WalletService walletService;

	@Autowired
	ContributionService contributionService;

	@Autowired
	AssetService assetService;


	public static void main(String[] args) {
		SpringApplication.run(YieldMeApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
		User admin = null;
		try {
			admin = userService.search("admin");
		} catch (Exception e) {
			e.getMessage();
		}
		if (admin == null) {
			admin = new User();
			admin.setUsername("admin");
			admin.setPassword("asd123");
			admin.setEmail("admin@yieldme.com");
			admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));

			userService.signup(admin);

		}

		try {
			Asset vt  = new Asset("VT", "VT", AssetCategory.ETF, Currency.DOLLAR);

			assetService.save(vt);
		} catch (Exception e) {
//			e.printStackTrace();
		}

	}
}
