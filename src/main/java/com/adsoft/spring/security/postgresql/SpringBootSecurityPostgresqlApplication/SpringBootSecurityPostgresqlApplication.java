package com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.ERole;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Role;
import com.adsoft.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.RoleRepository;

@SpringBootApplication
public class SpringBootSecurityPostgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSecurityPostgresqlApplication.class, args);
	}

	@Bean
	public CommandLineRunner initRoles(RoleRepository roleRepository) {
		return args -> {
			for (ERole roleName : ERole.values()) {
				roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
			}
		};
	}

}
