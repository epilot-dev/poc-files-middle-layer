package com.epilot.files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}
	
	@Bean
	ApplicationRunner applicationRunner(@Value("${jwks.url}") String jwksUrl) {
			return args -> {
					System.out.println("Using jwks url: " + jwksUrl);
			};
	}

}
