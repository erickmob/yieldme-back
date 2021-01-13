package com.erickmob.yieldme;

import com.erickmob.yieldme.model.Asset;
import com.erickmob.yieldme.model.AssetCategory;
import com.erickmob.yieldme.model.Currency;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.repository.UserRepository;
import com.erickmob.yieldme.service.AssetService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
public class YieldMeApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;


	@Autowired
	AssetService assetService;

	@Autowired
	PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(YieldMeApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {

		User user;
		try {
			user = userRepository.findByUsername("admin2").orElseThrow(() -> new UsernameNotFoundException("Username: admin  not found"));
		} catch (UsernameNotFoundException e) {
			log.info(e.getLocalizedMessage());
			log.info("Creating user ".concat("admin"));
			user = new User("admin2", "admin@gmail.com", true, "admin2",
					this.passwordEncoder.encode("asd123"),  Arrays.asList( "ROLE_USER", "ROLE_ADMIN"));
			userRepository.save(user);
			log.info("User admin created.");
		}


	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
