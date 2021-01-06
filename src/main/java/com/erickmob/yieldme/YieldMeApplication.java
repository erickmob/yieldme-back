package com.erickmob.yieldme;

import java.util.ArrayList;
import java.util.Arrays;
import com.erickmob.yieldme.model.Role;
import com.erickmob.yieldme.model.User;
import com.erickmob.yieldme.service.UserService;
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

	public static void main(String[] args) {
		SpringApplication.run(YieldMeApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... params) throws Exception {
//		User admin = new User();
//		admin.setUsername("admin");
//		admin.setPassword("admin");
//		admin.setEmail("admin@yieldme.com");
//		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_ADMIN)));
//
//		userService.signup(admin);
//
//		User client = new User();
//		client.setUsername("client");
//		client.setPassword("client");
//		client.setEmail("client@yieldme.com");
//		client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));
//
//		userService.signup(client);
	}
}
