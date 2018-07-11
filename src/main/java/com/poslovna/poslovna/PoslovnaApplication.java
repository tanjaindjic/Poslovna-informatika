package com.poslovna.poslovna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class PoslovnaApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoslovnaApplication.class, args);
	}
}
