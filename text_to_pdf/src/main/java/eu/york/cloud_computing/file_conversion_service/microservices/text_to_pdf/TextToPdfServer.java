package eu.york.cloud_computing.file_conversion_service.microservices.text_to_pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@EnableDiscoveryClient
@SpringBootApplication
public class TextToPdfServer {
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "text2pdf-server");
		SpringApplication.run(TextToPdfServer.class, args);
	}

	// Limit Uploaded PDF File to 10MB
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		// Maximum file size
		factory.setMaxFileSize(DataSize.parse("10MB"));
		// / Set the total upload data size
		factory.setMaxRequestSize(DataSize.parse("10MB"));
		return factory.createMultipartConfig() ;
	}
}
