package com.hoxify.ws;

import com.hoxify.ws.user.User;
import com.hoxify.ws.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class WsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsApplication.class, args);
	}

	@Bean
	CommandLineRunner createInitializeUser(UserService userService) {
		return args -> {
			User user = new User();
			user.setUsername("user1");
			user.setDisplayName("display1");
			user.setPassword("P4ssword");
			userService.save(user);
		};
	}

}
