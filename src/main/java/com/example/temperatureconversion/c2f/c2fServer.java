package com.example.temperatureconversion.c2f;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class c2fServer {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "c2f-server");
		SpringApplication.run(c2fServer.class, args);
	}
}
