package com.ashu.SpringSecurity;

import com.ashu.SpringSecurity.entity.Role;
import com.ashu.SpringSecurity.entity.Users;
import com.ashu.SpringSecurity.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableCaching
public class SpringSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserDetailsRepository userDetailsRepository, PasswordEncoder passwordEncoder) {
		return args -> {

			Users admin = new Users();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("123456"));
			admin.setRole(Role.ADMIN);

			userDetailsRepository.save(admin);

			Users user = new Users();
			user.setUsername("user");
			user.setPassword(passwordEncoder.encode("12345"));
			user.setRole(Role.USER);

			userDetailsRepository.save(user);
		};
	}
}
