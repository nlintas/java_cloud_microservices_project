package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class TextToPdfServer {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "text2pdf-server");
		SpringApplication.run(TextToPdfServer.class, args);
	}
}
