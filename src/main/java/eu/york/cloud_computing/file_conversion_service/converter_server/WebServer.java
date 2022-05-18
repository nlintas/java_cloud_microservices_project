package eu.york.cloud_computing.file_conversion_service.converter_server;

import eu.york.cloud_computing.file_conversion_service.converter_server.controllers.UserController;
import eu.york.cloud_computing.file_conversion_service.converter_server.controllers.HomeController;
import eu.york.cloud_computing.file_conversion_service.converter_server.services.TextToPDFPortal;
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

	public static final String C2F_SERVICE_URL = "http://c2f-service"; 
	
	@Autowired
    private static ApplicationContext applicationContext;
	
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
        System.out.println(applicationContext.containsBeanDefinition("c2fServer"));
    }

	@LoadBalanced 
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public TextToPDFPortal c2fService() {
		return new TextToPDFPortal(C2F_SERVICE_URL);
	}

	@Bean
	public UserController c2fController() {
		return new UserController(c2fService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}

}