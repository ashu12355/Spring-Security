package com.ashu.SpringSecurity;

import com.ashu.SpringSecurity.entity.Users;
import com.ashu.SpringSecurity.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			Users user = new Users();
			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("12345"));
			user.setRole("ROLE_ADMIN");

			userDetailsRepository.save(user);
		};
	}
}
