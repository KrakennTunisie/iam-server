package com.krakennTunisie.IAM_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IamServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IamServerApplication.class, args);
	}

}
