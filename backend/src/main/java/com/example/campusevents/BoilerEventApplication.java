package com.example.campusevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoilerEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoilerEventApplication.class, args);
	}

}
