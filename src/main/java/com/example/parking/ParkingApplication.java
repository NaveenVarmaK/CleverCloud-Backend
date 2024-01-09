package com.example.parking;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class ParkingApplication {

	@Value("${server.port}")
	private static String port;

	@Value("${bezkoder.openapi.dev-url}")
	private static String devUrl;

	String envPort = System.getenv("SPRINGBOOT_PORT");
	String envDevUrl = "http://localhost:" + envPort;


	public static void main(String[] args) {
		SpringApplication.run(ParkingApplication.class, args);
		System.out.println("Server started at port: " + port);
	}

}
