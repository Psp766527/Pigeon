package com.daimlertrucksasia.it.dsc.pigeon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PigeonApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigeonApplication.class, args);
	}

}
