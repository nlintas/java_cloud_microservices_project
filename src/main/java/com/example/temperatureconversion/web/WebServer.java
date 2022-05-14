package com.example.temperatureconversion.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
//@ComponentScan
@EnableDiscoveryClient
/**
 * Disable automatic detection of beans (classes annotated with @Component, @Repository, @Service, or @Controller) in packages in the classpath.
 * In our case below, all required beans are explicitly defined (hence included in the Application Context without the need to be auto-detected.
 **/
//@ComponentScan(useDefaultFilters = false) //Overrides @ComponentScan derived from @SrpingBootApplication									  
public class WebServer {

	//c2f-service below is the 'logical' name of the remote microservice that we want to 'hit'
	//It's specified in the config file c2f-server that corresponds to the server-side microservice.
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
    
    
	/**
	 * The following annotation indicates that RestTemplate bean below will use a RibbonLoadBalancerClient as an HttpRequestClient
	 * An HttpRequestClient is responsible for building an HTTP request.
	 * In case of multiple running server-side instances of a service, Ribbon requests are load balanced across the instances.
	 * Ribbon is responsible here for hooking up with the server and getting the ids of the running instances (if no service discoveryy registry is used)
	 * Btw, running multiple instances on the same host requires changing the way Spring assigns ids to running instances
	 * Ribbon enables the use of "logical identifiers" for the URI in the request.
	 * These logical identifiers are typically service names - e.g. c2f-service above. 
	 * No need to explicitly instantiate a Ribbon client (default settings used).
	 * Loadbalancing can be customised and configured (see https://spring.io/blog/2020/03/25/spring-tips-spring-cloud-loadbalancer)
	**/
	@LoadBalanced 
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

	//c2fService bean has a dependency on restTemplate
	@Bean
	public WebC2FService c2fService() {
		return new WebC2FService(C2F_SERVICE_URL);
	}

	//c2fController bean has a dependency on c2fService
	@Bean
	public WebConversionController c2fController() {
		return new WebConversionController(c2fService());
	}

	@Bean
	public HomeController homeController() {
		return new HomeController();
	}

}