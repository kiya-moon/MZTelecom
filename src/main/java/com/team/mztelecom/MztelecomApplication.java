package com.team.mztelecom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MztelecomApplication {

	public static void main(String[] args) {
		SpringApplication.run(MztelecomApplication.class, args);
	}

}
