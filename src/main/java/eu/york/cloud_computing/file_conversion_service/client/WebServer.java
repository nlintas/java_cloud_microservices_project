package eu.york.cloud_computing.file_conversion_service.client;

import eu.york.cloud_computing.file_conversion_service.client.controllers.HomeController;
import eu.york.cloud_computing.file_conversion_service.client.controllers.UserController;
import eu.york.cloud_computing.file_conversion_service.client.services.TextToPDFPortal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient

public class WebServer {

	public static final String TXT2PDF_SERVICE_URL = "http://text2pdf-microservice";
	
	@Autowired
    protected static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		System.setProperty("spring.config.name", "web-server");
		applicationContext = SpringApplication.run(WebServer.class, args);
		
		displayAllBeans();
	}
	
	public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
		// Loads properties file, name must match
        System.out.println(applicationContext.containsBeanDefinition("text2pdfServer"));
    }

	@LoadBalanced 
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public TextToPDFPortal text2pdfService() {
		return new TextToPDFPortal(TXT2PDF_SERVICE_URL);
	}

	@Bean
	public UserController text2pdfController() {
		return new UserController(text2pdfService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}

}